/*
 * @(#)jcov.h	1.28 06/10/10
 *
 * Copyright  1990-2008 Sun Microsystems, Inc. All Rights Reserved.  
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
 *
 */

#ifndef _JCOV_H
#define _JCOV_H

#include "jvmpi.h"
#include "jcov_types.h"
#include "jcov_hash.h"
#include "jcov_md.h"

#define MAX_PATH_LEN   1024
#define JCOV_FILE_MAJOR_VER 2
#define JCOV_FILE_MINOR_VER 0

#define CRT_STATEMENT       0x0001
#define CRT_BLOCK           0x0002
#define CRT_ASSIGNMENT      0x0004
#define CRT_FLOW_CONTROLLER 0x0008
#define CRT_FLOW_TARGET     0x0010
#define CRT_INVOKE          0x0020
#define CRT_CREATE          0x0040
#define CRT_BRANCH_TRUE     0x0080
#define CRT_BRANCH_FALSE    0x0100

typedef struct jcov_list_t_ {
    void *elem;
    struct jcov_list_t_ *next;
} jcov_list_t;

typedef struct {
    UINT16 pc;         /* starting pc for this item                  */
    UINT8  type;       /* item type                                  */
    UINT8  instr_type; /* type of the first instruction in this item */
    UINT32 where_line; /* line in source file or range_start         */
    UINT32 where_pos;  /* position in source file or range_end       */
    UINT32 count;      /* execution counter                          */
} cov_item_t;

typedef struct {
    UINT16 pc_start;  /* starting pc for this item                    */
    UINT16 pc_end;    /* ending pc for this item                      */
    INT32  rng_start; /* line/position in the source code range start */
    INT32  rng_end;   /* line/position in the source code range end   */
    UINT16 flags;     /* item type bits (block, flow target, etc)     */
} crt_entry_t;

typedef struct {
    jobjectID    id;           /* class ID                           */
    jobjectID    id_sav;       /* class ID before moving on heap     */
    char         *name;        /* class name                         */
    char         *src_name;    /* src file name                      */
    char         *timestamp;   /* timestamp as generated by compiler */
    SSIZE_T      num_methods;  /* number of methods                  */
    JVMPI_Method *methods;     /* methods                            */
    UINT16       access_flags; /* access flags                       */
    INT8         data_type;    /* type of gathered jcov data         */
    INT8         unloaded;     /* true when class is unloaded        */
} jcov_class_t;

typedef struct {
    jmethodID     id;            /* method ID                                          */
    char          *name;         /* method name                                        */
    char          *signature;    /* method signature                                   */
    UINT16        access_flags;  /* access flags                                       */
    int           *pc_cache;     /* keeps map of pc <-> cov_item correspondence        */
    SSIZE_T       pc_cache_size; /* size of the map (is equal to method's code length) */
    cov_item_t    *covtable;     /* coverage table                                     */
    SSIZE_T       covtable_size; /* coverage table size                                */
    jcov_class_t  *class;        /* class object defining this method                  */
} jcov_method_t;

typedef struct {
    char          *name;          /* class name                             */
    char          *src_name;      /* src file name                          */
    char          *timestamp;     /* timestamp as generated by compiler     */
    INT8          data_type;      /* type of gathered jcov data             */
    UINT16        access_flags;   /* access flags                           */
    jcov_method_t **method_cache; /* place to store methods of hooked class */
    SSIZE_T       methods_total;  /* number of methods in cache             */
} jcov_hooked_class_t;

typedef struct {
    JNIEnv      *id;                 /* thread ID                                           */
    jcov_hash_t *hooked_class_table; /* keeps the hooked class' (name, src_name, timestamp) */
} jcov_thread_t;

/* range of values of cov_item_t.type -
 * as generated by compiler
 */
enum {
    CT_METHOD = 1,
    CT_FICT_METHOD,
    CT_BLOCK,
    CT_FICT_RET,
    CT_CASE,
    CT_SWITCH_WO_DEF,
    CT_BRANCH_TRUE,
    CT_BRANCH_FALSE
};

/* range of values of cov_item_t.instr_type */
enum {
    INSTR_ANY = 0,
    INSTR_IF,
    INSTR_LOOKUPSW,
    INSTR_TABLESW
};


#define CALL(f) (jvmpi_interface->f)
#define INT32_AT(buf, off) (INT32)(buf[off] << 24 | buf[off+1] << 16 | buf[off+2] << 8 | buf[off+3])
#define INT16_AT(buf, off) (INT16)(buf[off] << 8 | buf[off+1])

extern JVMPI_Interface *jvmpi_interface;
extern jcov_hash_t *class_key_table;
extern jcov_hash_t *class_id_table;
extern jcov_hash_t *method_table;
extern jcov_hash_t *class_filt_table;
extern long memory_allocated;

#endif /* _JCOV_H */
