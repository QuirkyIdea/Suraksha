package com.example.suraksha.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0007\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000e\u001a\u00020\tH\u0002J\u0006\u0010\u000f\u001a\u00020\tJ\u0014\u0010\u0010\u001a\u00020\t2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\t0\bJ\u0006\u0010\u0012\u001a\u00020\tJ\u0006\u0010\u0013\u001a\u00020\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/example/suraksha/utils/PowerButtonDetector;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "lastTapTime", "", "onPowerButtonDetected", "Lkotlin/Function0;", "", "receiver", "Landroid/content/BroadcastReceiver;", "tapCount", "", "handlePowerButtonTap", "reset", "setOnPowerButtonListener", "listener", "startListening", "stopListening", "Companion", "app_debug"})
public final class PowerButtonDetector {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    private static final int TAP_COUNT_THRESHOLD = 4;
    private static final long TAP_TIME_WINDOW = 3000L;
    private int tapCount = 0;
    private long lastTapTime = 0L;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function0<kotlin.Unit> onPowerButtonDetected;
    @org.jetbrains.annotations.Nullable()
    private android.content.BroadcastReceiver receiver;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.suraksha.utils.PowerButtonDetector.Companion Companion = null;
    
    public PowerButtonDetector(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void setOnPowerButtonListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> listener) {
    }
    
    public final void startListening() {
    }
    
    public final void stopListening() {
    }
    
    private final void handlePowerButtonTap() {
    }
    
    public final void reset() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/example/suraksha/utils/PowerButtonDetector$Companion;", "", "()V", "TAP_COUNT_THRESHOLD", "", "TAP_TIME_WINDOW", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}