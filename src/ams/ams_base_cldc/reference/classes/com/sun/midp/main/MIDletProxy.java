/*
 *
 *
 * Copyright  1990-2007 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included at /legal/license.txt).
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions.
 */

package com.sun.midp.main;

import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.midp.lcdui.ForegroundEventProducer;
import com.sun.midp.midlet.MIDletEventProducer;
import com.sun.midp.midlet.MIDletSuite;
import com.sun.midp.suspend.SuspendDependency;

/**
 * Represents the state of a running MIDlet and its Display so that objects
 * do not have to be shared across Isolates. The states in this object are
 * updated by the MIDletProxyList upon receiving a notification event.
 * This class also provides methods for asynchronously changing a MIDlet's
 * state.
 */
public class MIDletProxy implements SuspendDependency {

    /** Constant for active state of a MIDlet. */
    public static final int MIDLET_ACTIVE = 0;

    /** Constant for paused state of a MIDlet. */
    public static final int MIDLET_PAUSED = 1;

    /** Constant for destroyed state of a MIDlet. */
    public static final int MIDLET_DESTROYED = 2;

    /** Constant for suspended state of a MIDlet. */
    public static final int MIDLET_SUSPENDED = 3;

    /** Cached reference to the ForegroundEventProducer. */
    private static ForegroundEventProducer foregroundEventProducer;

    /** Cached reference to the MIDletEventProducer. */
    private static MIDletEventProducer midletEventProducer;

    /** ID given to this midlet by an external application manager. */
    private int externalId;

    /** ID of the Isolate the MIDlet is running in. */
    private int isolateId;

    /** ID of the MIDlet's Display. */
    private int displayId;

    /** ID of the suite the MIDlet belongs to. */
    private int suiteId;

    /** Class name of the MIDlet. */
    private String className;

    /** Display name of the MIDlet to show the user. */
    private String displayName;

    /**
     * MIDlet life cycle state. Will be either MIDLET_ACTIVE, MIDLET_PAUSED,
     * or MIDLET_DESTROYED.
     */
    private int midletState;

    /** Indicates that the midlet was just created. */
    boolean wasNotActive;

    /** True if the MIDlet want's its Display in the foreground. */
    private boolean wantsForegroundState;

    /** True if the MIDlet has the foreground at least once. */
    private boolean requestedForeground;

    /** The display that is preempting this MIDlet. */
    private MIDletProxy preempting;

    /** True if alert is waiting for the foreground. */
    private boolean alertWaiting;

    /**
     * The display to put in the foreground after this display stops
     * preempting. If no display in this isolate had the foreground
     * then this will be null.
     */
    private MIDletProxy preempted;

    /**
     * Timer for the MIDlet proxy.  Used when a midlet is hanging.
     */
    private Timer proxyTimer;

    /** Parent list. */
    private MIDletProxyList parent;

    /**
     * Initialize the MIDletProxy class. Should only be called by the
     * MIDletProxyList.
     *
     * @param  theForegroundEventProducer reference to the event producer
     * @param  theMIDletEventProducer reference to the event producer
     */
    static void initClass(
        ForegroundEventProducer theForegroundEventProducer,
        MIDletEventProducer theMIDletEventProducer) {

        foregroundEventProducer = theForegroundEventProducer;
        midletEventProducer = theMIDletEventProducer;
    }

    /**
     * Construct a new MIDletProxy.
     *
     * @param  theParentList parent MIDlet proxy list
     * @param  theExternalAppId ID of given by an external application manager
     * @param  theIsolateId ID of the Isolate the MIDlet is running in.
     * @param  theSuiteId   ID of the suite MIDlet
     * @param  theClassName Class name of the MIDlet
     * @param  theDisplayName Display name of the MIDlet to show the user
     * @param  theMidletState MIDlet lifecycle state.
     */
    MIDletProxy(MIDletProxyList theParentList, int theExternalAppId,
         int theIsolateId, int theSuiteId,
         String theClassName, String theDisplayName, int theMidletState) {

        parent = theParentList;
        externalId = theExternalAppId;
        isolateId = theIsolateId;
        suiteId = theSuiteId;
        className = theClassName;
        displayName = theDisplayName;
        midletState = theMidletState;
        wasNotActive = true;
    }

    /**
     * Get the external application ID used for forwarding changes.
     *
     * @return ID assigned by the external application manager
     */
    public int getExternalAppId() {
        return externalId;
    }

    /**
     * Get the ID of the Isolate the MIDlet is running in. Public for testing
     * purposes.
     *
     * @return ID of the Isolate the MIDlet is running in
     */
    public int getIsolateId() {
        return isolateId;
    }

    /**
     * Sets the ID of the MIDlet's Display.
     *
     * @param id of the MIDlet's Display
     */
    void setDisplayId(int id) {
        displayId = id;
    }

    /**
     * Get the ID of the MIDlet's Display. Public for testing purposes.
     *
     * @return ID of the MIDlet's Display
     */
    public int getDisplayId() {
        return displayId;
    }

    /**
     * Get the ID of the MIDlet's suite.
     *
     * @return ID of the MIDlet's suite
     */
    public int getSuiteId() {
        return suiteId;
    }

    /**
     * Get the class name of the MIDlet.
     *
     * @return class name of the MIDlet
     */
    public String getClassName() {
        return className;
    }

    /**
     * Get the Display name of the MIDlet.
     *
     * @return Display name of the MIDlet
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Set the MIDlet cycle state. Called by the
     * MIDlet proxy list when it receives an event from the MIDlet
     * to update this value.
     *
     * @param newMidletState new MIDlet state
     */
    void setMidletState(int newMidletState) {
        midletState = newMidletState;

        /* this is called by the MIDlet proxy list after MIDlet.startApp()
         * has already completed successfully */
        if (newMidletState == MIDLET_ACTIVE) {
            wasNotActive = false;
        }
    }

    /**
     * Get the MIDlet lifecycle state.
     *
     * @return MIDlet state
     */
    public int getMidletState() {
        return midletState;
    }

    /**
     * Set the wants foreground state in the proxy. Called by the
     * MIDlet proxy list when it receives an event from the MIDlet's
     * display to update this value.
     *
     * @param newWantsForeground new wants foreground value.
     * @param isAlert true if the displayable requesting the foreground,
     *        is an Alert, this parameter is ignored if newWantsForeground
     *        is false
     */
    void setWantsForeground(boolean newWantsForeground, boolean isAlert) {
        wantsForegroundState = newWantsForeground;

        if (newWantsForeground) {
            requestedForeground = true;
            alertWaiting = isAlert;
        } else {
            alertWaiting = false;
        }
    }

    /**
     * Check if the MIDlet want's its Display in the foreground.
     *
     * @return true if the MIDlet want's its Display in the foreground
     */
    public boolean wantsForeground() {
        return wantsForegroundState;
    }

    /**
     * Check if the MIDlet has not created its display.
     *
     * @return true if the MIDlet has no display.
     */
    public boolean noDisplay() {
        return displayId == 0;
    }

    /**
     * Check if the MIDlet has not set a displayable in its display.
     * Used by foreground selector to determine if the MIDlet it is
     * about to put in the foreground will draw the screen.
     *
     * @return true if the MIDlet has no displayable.
     */
    public boolean noDisplayable() {
        return !requestedForeground;
    }

    /**
     * Set the proxy of the display that is preempting this MIDlet.
     *
     * @param preemptingDisplay the preempting display
     */
    void setPreemptingDisplay(MIDletProxy preemptingDisplay) {
        // Turn on the user notification status for this proxy
        if (preemptingDisplay != null) {
            alertWaiting = true;
        } else {
            if (preempting != null) {
                /*
                 * There could be a proxy timer waiting to destroy the
                 * isolate if the user ended the alert with the end MIDlet
                 * button, so cancel the timer.
                 */
                preempting.setTimer(null);
            }

            alertWaiting = false;
        }

        preempting = preemptingDisplay;
    }

    /**
     * Get the proxy of the display that is preempting this MIDlet.
     *
     * @return the preempting display
     */
    MIDletProxy getPreemptingDisplay() {
        return preempting;
    }

    /**
     * Set the proxy of the MIDlet that should get the foreground
     * after preempting is done.
     *
     * @param preemptedDisplay the preempted display
     */
    void setPreemptedMidlet(MIDletProxy preemptedDisplay) {
        preempted = preemptedDisplay;
    }

    /**
     * Get the proxy of the MIDlet that should get the foreground
     * after preempting is done.
     *
     * @return the preempted display or null for none
     */
    MIDletProxy getPreemptedMidlet() {
        return preempted;
    }

    /**
     * Called to determine if alert is waiting for the foreground.
     *
     * @return true if an alert of the MIDlet is waiting in background.
     */
    public boolean isAlertWaiting() {
        return alertWaiting;
    }

    /**
     * Asynchronously change the MIDlet's state to active.
     *
     * This method does NOT change the state in the proxy, but
     * sends a activate MIDlet event to the MIDlet's Display.
     * The state in the proxy is only update when the MIDlet sends
     * a MIDlet activated event to the proxy list.
     * 
     * @return <code>true</code> if the MIDlet.startApp() has not been
     * called yet and only the MIDlet's isolate has been resumed.
     * <code>false</code>, otherwise.
     */
    public boolean activateMidlet() {
        if (midletState != MIDLET_DESTROYED && midletState != MIDLET_ACTIVE) {
            if (midletState == MIDLET_SUSPENDED) {
                continueIsolate();
                if (wasNotActive) {
                    /* MIDlet was paused/resumed during startup,
                     * state should be MIDLET_PAUSED until activated */
                    midletState = MIDLET_PAUSED;
                }
                return true;
            } else {
                midletEventProducer.sendMIDletActivateEvent(isolateId, className);
            }
        }
        return false;
    }

    /**
     * Asynchronously change the MIDlet's state to paused.
     *
     * This method does NOT change the state in the proxy, but
     * sends a pause MIDlet event to the MIDlet's Display.
     * The state in the proxy is only update when the MIDlet sends
     * a MIDlet paused event to the proxy list.
     * 
     * @return <code>true</code> if the MIDlet.startApp() has not been
     * called yet and only the MIDlet's isolate has been suspended.
     * <code>false</code>, otherwise.
     */
    public boolean pauseMidlet() {
        if (midletState != MIDLET_DESTROYED) {

            if (suiteId == MIDletSuite.UNUSED_SUITE_ID && className == null) {
                // proxy of preemting display does not need to be paused
                return false;
            }

            /* in MVM mode, don't pause the AMS isolate */
            if (MIDletSuiteUtils.isMVM() &&
                isolateId == MIDletSuiteUtils.getAmsIsolateId() &&
                !("true".equals(System.getProperty("running_local")))) {
                return false;
            }

            if (wasNotActive) {
                suspendIsolate();
                return true;
            }

            if (midletState != MIDLET_PAUSED &&
                midletState != MIDLET_SUSPENDED) {
                midletEventProducer.sendMIDletPauseEvent(isolateId, className);

                /* start a timer to kill the MIDlet
                 * if the pause process takes too long */
                if (getTimer() == null) {
                    MIDletDestroyTimer.start(this, parent);
                }
            }
        }
        return false;
    }

    /**
     * Change the MIDlet's state to suspended
     * 
     * @return <code>true</code> if isolate is suspended successfully,
     *         <code>false</code> otherwise
     */
    public boolean suspendIsolate() {
        if (midletState != MIDLET_DESTROYED && midletState != MIDLET_SUSPENDED) {
            if (MIDletProxyUtils.suspendIsolate(isolateId)) {
                midletState = MIDLET_SUSPENDED;
                return true;
            }
        }
        return false;
    }

    /**
     * Change the MIDlet's state to active
     */
    public boolean continueIsolate() {
        if (midletState != MIDLET_DESTROYED && midletState != MIDLET_ACTIVE) {
            if (MIDletProxyUtils.continueIsolate(isolateId)) {
                midletState = MIDLET_ACTIVE;
                return true;
            }
        }
        return false;
    }

    /**
     * Terminates ther MIDlet if it is neither paused nor destroyed.
     */
    public void terminateNotPausedMidlet() {
        if (midletState != MIDLET_DESTROYED && midletState != MIDLET_PAUSED) {
            MIDletProxyUtils.terminateMIDletIsolate(this, parent);
        }
    }

    /**
     * Asynchronously change the MIDlet's state to destroyed.
     *
     * This method does NOT change the state in the proxy, but
     * sends request to destroy MIDlet event to the AMS.
     * The state in the proxy is only update when the MIDlet sends
     * a MIDlet destroyed event to the proxy list.
     */
    public void destroyMidlet(int timeout) {
        if (midletState != MIDLET_DESTROYED) {
            if (getTimer() != null) {
                // A destroy MIDlet event has been sent.
                return;
            }

            MIDletDestroyTimer.setTimeout(timeout);
            MIDletDestroyTimer.start(this, parent);

            midletEventProducer.sendMIDletDestroyEvent(isolateId, className);
        }
    }

    /** 
     * Process a MIDlet destroyed notification.
     */
    void destroyedNotification() {
        setTimer(null);
        setMidletState(MIDLET_DESTROYED);
    }

    /** 
     * Process a MIDlet paused notification.
     */
    void pausedNotification() {
        setTimer(null);
        setMidletState(MIDLET_PAUSED);
    }

    /**
     * Notify the midlet's display of a foreground change. Called by
     * the MIDlet proxy list to notify the old and new foreground displays
     * of a foreground change.
     *
     * @param hasForeground true if the target is being put in the foreground
     */
    void notifyMIDletHasForeground(boolean hasForeground) {
        if (hasForeground) {
            alertWaiting = false;
            foregroundEventProducer.sendDisplayForegroundNotifyEvent(
                isolateId, displayId);
        } else {
            foregroundEventProducer.sendDisplayBackgroundNotifyEvent(
                isolateId, displayId);
        }
    }

    /**
     * Sets the timer object
     *
     * @param t Timer object
     */
    void setTimer(Timer t) {
        proxyTimer = t;
    }

    /**
     * Gets the timer object
     *
     * @return Timer
     */
    Timer getTimer() {
        return proxyTimer;
    }

    /**
     * Print the state of the proxy.
     *
     * @return printable representation of the state of this object
     */
    public String toString() {
        return "MIDletProxy: suite id = " + suiteId +
            ", class name = " + className +
            ", display name = " + displayName +
            ", isolate id = " + isolateId +
            ", display id = " + displayId +
            ", midlet state = " + midletState +
            ", wantsForeground = " + wantsForegroundState +
            ", requestedForeground = " + requestedForeground +
            ", alertWaiting = " + alertWaiting;
    }
}

/**
 * If the MIDlet is hanging this class will start a
 * timer and terminate the MIDlet when the timer
 * expires.  The timer will not work in SVM mode.
 */
class MIDletDestroyTimer {
    /** Timeout to let MIDlet destroy itself. */
    private static int timeout =
        1000 * Configuration.getIntProperty("destoryMIDletTimeout", 5);

    /**
     * Starts timer for the specified MIDlet (proxy) .
     *
     * @param mp MIDletProxy to terminate if not destroyed in time
     * @param mpl the MIDletProxyList
     */
    static void start(final MIDletProxy mp, final MIDletProxyList mpl) {
        Timer timer = new Timer();
        mp.setTimer(timer);

        TimerTask task = new TimerTask() {
            /** Terminates MIDlet isolate and updates the proxy list. */
            public void run() {
                if (mp.getTimer() != null) {
                    MIDletProxyUtils.terminateMIDletIsolate(mp, mpl);
                }
                cancel();
            }
        };

        timer.schedule(task, timeout);
    }

    /**
     * Set a timeout for the destroy MIDlet timer
     * 
     * @param newTimeout the timeout to set
     */
    static void setTimeout(int newTimeout) {
        if (newTimeout > 0) {
            timeout = newTimeout;
        }
    }
}
