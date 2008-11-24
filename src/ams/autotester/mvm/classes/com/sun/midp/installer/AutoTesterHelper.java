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

package com.sun.midp.installer;

import java.io.IOException;
import com.sun.midp.midletsuite.MIDletSuiteLockedException;

import com.sun.cldc.isolate.*;

import com.sun.midp.i18n.Resource;
import com.sun.midp.i18n.ResourceConstants;

import com.sun.midp.main.AmsUtil;
import com.sun.midp.main.MIDletSuiteUtils;

import com.sun.midp.midletsuite.MIDletInfo;
import com.sun.midp.midletsuite.MIDletSuiteStorage;
import com.sun.midp.midlet.MIDletSuite;
import com.sun.midp.configurator.Constants;

import com.sun.midp.events.*;

/**
 * Implements auto testing functionality for MVM mode.
 */
class AutoTesterHelper extends AutoTesterHelperBase 
    implements EventListener {

    /** True if all events in our queue were processed. */
    private boolean eventsInQueueProcessed;

    /** Our event queue. */
    private EventQueue eventQueue;

    /** ID of the test suite being run */
    private int suiteId = MIDletSuite.UNUSED_SUITE_ID;

    /**
     * Constructor.
     *
     * @param inp_url URL of the test suite
     * @param inp_domain security domain to assign to unsigned suites
     * @param inp_count how many iterations to run the suite
     */
    AutoTesterHelper(String inp_url, String inp_domain, int inp_count) {
        super(inp_url, inp_domain, inp_count);

        eventQueue = EventQueue.getEventQueue();
        eventQueue.registerEventListener(EventTypes.AUTOTESTER_EVENT, this);        
    }

    /**
     * Preprocess an event that is being posted to the event queue.
     * This method will get called in the thread that posted the event.
     *
     * @param event event being posted
     *
     * @param waitingEvent previous event of this type waiting in the
     *     queue to be processed
     *
     * @return true to allow the post to continue, false to not post the
     *     event to the queue
     */
    public boolean preprocess(Event event, Event waitingEvent) {
        return true;
    }

    /**
     * Process an event.
     * This method will get called in the event queue processing thread.
     *
     * @param event event to process
     */
    public void process(Event event) {
        NativeEvent nativeEvent = (NativeEvent)event;

        switch (nativeEvent.getType()) {
            case EventTypes.AUTOTESTER_EVENT: {
                synchronized (this) {
                    eventsInQueueProcessed = true;
                    notify();
                }
                break;
            }
        }
    }    

    /**
     * Installs and performs the tests.
     */
    void installAndPerformTests() 
        throws Exception {

        for (; loopCount != 0; ) {
            // force an overwrite and remove the RMS data
            suiteId = installer.installJad(url, 
                    Constants.INTERNAL_STORAGE_ID, true, true, null);

            MIDletInfo midletInfo = getFirstMIDletOfSuite(suiteId);
            Isolate[] isolatesBefore = Isolate.getIsolates();

            Isolate testIsolate = AmsUtil.startMidletInNewIsolate(suiteId,
                    midletInfo.classname, midletInfo.name, null,
                    null, null);

            testIsolate.waitForExit();
            
            boolean newIsolatesFound;
            do {
                newIsolatesFound = false;

                /*
                 * Send an event to ourselves.
                 * Main idea of it is to process all events that are in the
                 * queue at the moment when the test isolate has exited
                 * (because when testing CHAPI there may be requests to
                 * start new isolates). When this event arrives, all events
                 * that were placed in the queue before it are guaranteed
                 * to be processed.
                 */
                synchronized (this) {
                    eventsInQueueProcessed = false;

                    NativeEvent event = new NativeEvent(
                            EventTypes.AUTOTESTER_EVENT);
                    eventQueue.sendNativeEventToIsolate(event,
                            MIDletSuiteUtils.getIsolateId());
                    
                    // and wait until it arrives
                    do {
                        try {
                            wait();
                        } catch(InterruptedException ie) {
                            // ignore
                        }
                    } while (!eventsInQueueProcessed);
                }

                Isolate[] isolatesAfter = Isolate.getIsolates();

                /*
                 * Wait for termination of all isolates contained in
                 * isolatesAfter[], but not in isolatesBefore[].
                 * This is needed to pass some tests (for example, CHAPI)
                 * that starting several isolates.
                 */
                int i, j;
                for (i = 0; i < isolatesAfter.length; i++) {
                    for (j = 0; j < isolatesBefore.length; j++) {
                        try {
                            if (isolatesBefore[j].equals(isolatesAfter[i])) {
                                break;
                            }
                        } catch (Exception e) {
                            // isolatesAfter[i] might already exit,
                            // no need to wait for it
                            break;
                        }
                    }

                    if (j == isolatesBefore.length) {
                        try {
                            newIsolatesFound = true;
                            isolatesAfter[i].waitForExit();
                        } catch (Exception e) {
                            // ignore: the isolate might already exit
                        }
                    }
                }
            } while (newIsolatesFound);

            if (loopCount > 0) {
                loopCount -= 1;
            }
        }

        if (midletSuiteStorage != null &&
                suiteId != MIDletSuite.UNUSED_SUITE_ID) {
            try {
                midletSuiteStorage.remove(suiteId);
            } catch (Throwable ex) {
                // ignore
            }
        }
    }

    /**
     * Gets ID of the current test suite.
     *
     * @return ID of the current test suite
     */
    int getTestSuiteId() {
        return suiteId;
    }
}
