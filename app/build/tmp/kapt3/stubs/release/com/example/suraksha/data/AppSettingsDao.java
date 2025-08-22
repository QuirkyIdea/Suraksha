package com.example.suraksha.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\f\u00a8\u0006\u000e"}, d2 = {"Lcom/example/suraksha/data/AppSettingsDao;", "", "deleteSetting", "", "key", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSetting", "Lcom/example/suraksha/data/AppSettings;", "getSettingValue", "insertSetting", "setting", "(Lcom/example/suraksha/data/AppSettings;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSetting", "app_release"})
@androidx.room.Dao()
public abstract interface AppSettingsDao {
    
    @androidx.room.Query(value = "SELECT * FROM app_settings WHERE `key` = :key")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSetting(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.suraksha.data.AppSettings> $completion);
    
    @androidx.room.Query(value = "SELECT value FROM app_settings WHERE `key` = :key")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSettingValue(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSetting(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.data.AppSettings setting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateSetting(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.data.AppSettings setting, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM app_settings WHERE `key` = :key")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteSetting(@org.jetbrains.annotations.NotNull()
    java.lang.String key, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}