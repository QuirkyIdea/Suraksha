package com.example.suraksha.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0012\b\u0007\u0018\u0000 92\u00020\u0001:\u00029:B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\tH\u0002J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\t2\u0006\u0010\u001a\u001a\u00020\tH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0082@\u00a2\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020\tH\u0002J\b\u0010!\u001a\u00020\u001cH\u0002J\u0012\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\u001cH\u0016J\b\u0010\'\u001a\u00020\u001cH\u0016J\"\u0010(\u001a\u00020)2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010*\u001a\u00020)2\u0006\u0010+\u001a\u00020)H\u0016J\b\u0010,\u001a\u00020\u001cH\u0002J\b\u0010-\u001a\u00020\u001cH\u0002J\u0018\u0010.\u001a\u00020\u001c2\b\u0010/\u001a\u0004\u0018\u00010\u001eH\u0082@\u00a2\u0006\u0002\u00100J\u0018\u00101\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\t2\u0006\u00103\u001a\u00020\tH\u0002J\u0018\u00104\u001a\u00020\u001c2\b\u0010/\u001a\u0004\u0018\u00010\u001eH\u0082@\u00a2\u0006\u0002\u00100J\u0018\u00105\u001a\u00020\u001c2\u0006\u00102\u001a\u00020\t2\u0006\u00103\u001a\u00020\tH\u0002J\u0012\u00106\u001a\u00020\u001c2\b\u0010/\u001a\u0004\u0018\u00010\u001eH\u0002J\b\u00107\u001a\u00020\u001cH\u0002J\b\u00108\u001a\u00020\u001cH\u0002R\u0012\u0010\u0003\u001a\u00060\u0004R\u00020\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2 = {"Lcom/example/suraksha/services/SafetyService;", "Landroid/app/Service;", "()V", "binder", "Lcom/example/suraksha/services/SafetyService$SafetyBinder;", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "indiaEmergencyNumbers", "", "", "isPaused", "", "isRecording", "mediaRecorder", "Landroid/media/MediaRecorder;", "recordingFile", "Ljava/io/File;", "repository", "Lcom/example/suraksha/data/SurakshaRepository;", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "buildEmergencyMessage", "locationText", "createNotification", "Landroid/app/Notification;", "title", "content", "createNotificationChannel", "", "getCurrentLocation", "Landroid/location/Location;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserName", "handleSOS", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "", "flags", "startId", "pauseRecording", "resumeRecording", "sendEmergencySMS", "location", "(Landroid/location/Location;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendSMS", "phoneNumber", "message", "sendToEmergencyNumbers", "sendWhatsAppMessage", "showEmergencyNotification", "startRecording", "stopRecording", "Companion", "SafetyBinder", "app_debug"})
public final class SafetyService extends android.app.Service {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_SOS = "com.example.suraksha.SOS";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_START_RECORDING = "com.example.suraksha.START_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_STOP_RECORDING = "com.example.suraksha.STOP_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_PAUSE_RECORDING = "com.example.suraksha.PAUSE_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ACTION_RESUME_RECORDING = "com.example.suraksha.RESUME_RECORDING";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_ID = "suraksha_safety_channel";
    public static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull()
    private final com.example.suraksha.services.SafetyService.SafetyBinder binder = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    private com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient;
    private com.example.suraksha.data.SurakshaRepository repository;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaRecorder mediaRecorder;
    @org.jetbrains.annotations.Nullable()
    private java.io.File recordingFile;
    private boolean isRecording = false;
    private boolean isPaused = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> indiaEmergencyNumbers = null;
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
    @org.jetbrains.annotations.NotNull()
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable()
    android.content.Intent intent) {
        return null;
    }
    
    private final void handleSOS() {
    }
    
    private final java.lang.Object getCurrentLocation(kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    private final java.lang.Object sendEmergencySMS(android.location.Location location, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object sendToEmergencyNumbers(android.location.Location location, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String buildEmergencyMessage(java.lang.String locationText) {
        return null;
    }
    
    private final java.lang.String getUserName() {
        return null;
    }
    
    private final void sendSMS(java.lang.String phoneNumber, java.lang.String message) {
    }
    
    private final void sendWhatsAppMessage(java.lang.String phoneNumber, java.lang.String message) {
    }
    
    private final void startRecording() {
    }
    
    private final void stopRecording() {
    }
    
    private final void pauseRecording() {
    }
    
    private final void resumeRecording() {
    }
    
    private final void showEmergencyNotification(android.location.Location location) {
    }
    
    private final android.app.Notification createNotification(java.lang.String title, java.lang.String content) {
        return null;
    }
    
    private final void createNotificationChannel() {
    }
    
    @java.lang.Override()
    public void onDestroy() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/example/suraksha/services/SafetyService$Companion;", "", "()V", "ACTION_PAUSE_RECORDING", "", "ACTION_RESUME_RECORDING", "ACTION_SOS", "ACTION_START_RECORDING", "ACTION_STOP_RECORDING", "CHANNEL_ID", "NOTIFICATION_ID", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/example/suraksha/services/SafetyService$SafetyBinder;", "Landroid/os/Binder;", "(Lcom/example/suraksha/services/SafetyService;)V", "getService", "Lcom/example/suraksha/services/SafetyService;", "app_debug"})
    public final class SafetyBinder extends android.os.Binder {
        
        public SafetyBinder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.example.suraksha.services.SafetyService getService() {
            return null;
        }
    }
}