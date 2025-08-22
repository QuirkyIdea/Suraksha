package com.example.suraksha.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/example/suraksha/data/SurakshaDatabase;", "Landroidx/room/RoomDatabase;", "()V", "appSettingsDao", "Lcom/example/suraksha/data/AppSettingsDao;", "emergencyContactDao", "Lcom/example/suraksha/data/EmergencyContactDao;", "safetyRecordDao", "Lcom/example/suraksha/data/SafetyRecordDao;", "app_debug"})
@androidx.room.Database(entities = {com.example.suraksha.data.EmergencyContact.class, com.example.suraksha.data.AppSettings.class, com.example.suraksha.data.SafetyRecord.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters(value = {com.example.suraksha.data.Converters.class})
public abstract class SurakshaDatabase extends androidx.room.RoomDatabase {
    
    public SurakshaDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.suraksha.data.EmergencyContactDao emergencyContactDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.suraksha.data.AppSettingsDao appSettingsDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.suraksha.data.SafetyRecordDao safetyRecordDao();
}