package com.example.suraksha.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0002J\b\u0010\u0012\u001a\u00020\u0010H\u0002J\u0012\u0010\u0013\u001a\u00020\u00102\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0010H\u0014J\b\u0010\u0017\u001a\u00020\u0010H\u0002J\b\u0010\u0018\u001a\u00020\u0010H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/example/suraksha/ui/screens/FakeCallActivity;", "Landroidx/activity/ComponentActivity;", "()V", "audioManager", "Landroid/media/AudioManager;", "emergencyContact", "Lcom/example/suraksha/data/EmergencyContact;", "mediaPlayer", "Landroid/media/MediaPlayer;", "repository", "Lcom/example/suraksha/data/SurakshaRepository;", "telephonyManager", "Landroid/telephony/TelephonyManager;", "vibrator", "Landroid/os/Vibrator;", "answerCall", "", "declineCall", "endCall", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "startFakeCall", "startVibration", "app_debug"})
public final class FakeCallActivity extends androidx.activity.ComponentActivity {
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer mediaPlayer;
    @org.jetbrains.annotations.Nullable()
    private android.os.Vibrator vibrator;
    @org.jetbrains.annotations.Nullable()
    private android.media.AudioManager audioManager;
    @org.jetbrains.annotations.Nullable()
    private android.telephony.TelephonyManager telephonyManager;
    @org.jetbrains.annotations.Nullable()
    private com.example.suraksha.data.SurakshaRepository repository;
    @org.jetbrains.annotations.Nullable()
    private com.example.suraksha.data.EmergencyContact emergencyContact;
    
    public FakeCallActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void startFakeCall() {
    }
    
    private final void startVibration() {
    }
    
    private final void answerCall() {
    }
    
    private final void declineCall() {
    }
    
    private final void endCall() {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
}