package com.example.suraksha.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\'J\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\t\u001a\u00020\nH\'J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u0014"}, d2 = {"Lcom/example/suraksha/data/SafetyRecordDao;", "", "getAllRecords", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/suraksha/data/SafetyRecord;", "getLatestUnresolvedRecord", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecordsByType", "type", "", "insertRecord", "", "record", "(Lcom/example/suraksha/data/SafetyRecord;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markRecordResolved", "", "recordId", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRecord", "app_release"})
@androidx.room.Dao()
public abstract interface SafetyRecordDao {
    
    @androidx.room.Query(value = "SELECT * FROM safety_records ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.suraksha.data.SafetyRecord>> getAllRecords();
    
    @androidx.room.Query(value = "SELECT * FROM safety_records WHERE type = :type ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.suraksha.data.SafetyRecord>> getRecordsByType(@org.jetbrains.annotations.NotNull()
    java.lang.String type);
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertRecord(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.data.SafetyRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateRecord(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.data.SafetyRecord record, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE safety_records SET isResolved = 1 WHERE id = :recordId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markRecordResolved(long recordId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM safety_records WHERE isResolved = 0 ORDER BY timestamp DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLatestUnresolvedRecord(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.suraksha.data.SafetyRecord> $completion);
}