package com.example.suraksha.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\b\u0007\u0018\u0000 $2\u00020\u0001:\u0001$B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0002J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0082@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u000eH\u0016J\b\u0010\u0016\u001a\u00020\u000eH\u0016J\"\u0010\u0017\u001a\u00020\u00182\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0018H\u0016J\u001a\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0004H\u0002J\u0016\u0010\u001f\u001a\u00020\u000e2\u0006\u0010 \u001a\u00020!H\u0082@\u00a2\u0006\u0002\u0010\"J\u000e\u0010#\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010\u0010R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006%"}, d2 = {"Lcom/example/suraksha/services/SafetyService;", "Landroid/app/Service;", "()V", "currentLocation", "Landroid/location/Location;", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "repository", "Lcom/example/suraksha/data/SurakshaRepository;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "createNotification", "Landroid/app/Notification;", "createNotificationChannel", "", "getCurrentLocation", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "sendEmergencySMS", "phoneNumber", "", "location", "startEmergencyRecording", "recordId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "triggerSOS", "Companion", "app_release"})
public final class SafetyService extends android.app.Service {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID = "SurakshaSafetyChannel";
    public static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_SOS = "com.example.suraksha.SOS";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP_SERVICE = "com.example.suraksha.STOP_SERVICE";
    private com.example.suraksha.data.SurakshaRepository repository;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    @org.jetbrains.annotations.Nullable()
    private android.location.Location currentLocation;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.suraksha.services.SafetyService.Companion Companion = null;
    
    public SafetyService() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    @java.lang.Override()
    public int onStartCommand(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    private final void createNotificationChannel() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    private final java.lang.Object triggerSOS(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object getCurrentLocation(kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    private final void sendEmergencySMS(java.lang.String phoneNumber, android.location.Location location) {
    }
    
    private final java.lang.Object startEmergencyRecording(long recordId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/example/suraksha/services/SafetyService$Companion;", "", "()V", "ACTION_SOS", "", "ACTION_STOP_SERVICE", "CHANNEL_ID", "NOTIFICATION_ID", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}