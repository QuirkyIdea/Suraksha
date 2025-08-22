package com.example.suraksha.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0006\u0010\u0014\u001a\u00020\rJ\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\nJ\b\u0010\u0017\u001a\u00020\rH\u0002J\u000e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0006J\u001a\u0010\u001a\u001a\u00020\r2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r0\fJ\u0014\u0010\u001c\u001a\u00020\r2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\r0\u000fJ\u0006\u0010\u001d\u001a\u00020\rJ\u0006\u0010\u001e\u001a\u00020\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/suraksha/utils/VoiceCommandDetector;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "customCommand", "", "detectorScope", "Lkotlinx/coroutines/CoroutineScope;", "isListening", "", "onErrorListener", "Lkotlin/Function1;", "", "onVoiceCommandListener", "Lkotlin/Function0;", "speechRecognizer", "Landroid/speech/SpeechRecognizer;", "createRecognitionListener", "Landroid/speech/RecognitionListener;", "destroy", "getCustomCommand", "isCurrentlyListening", "restartListening", "setCustomCommand", "command", "setOnErrorListener", "listener", "setOnVoiceCommandListener", "startListening", "stopListening", "app_debug"})
public final class VoiceCommandDetector {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.speech.SpeechRecognizer speechRecognizer;
    private boolean isListening = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String customCommand = "emergency help me";
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function0<kotlin.Unit> onVoiceCommandListener;
    @org.jetbrains.annotations.Nullable()
    private kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onErrorListener;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope detectorScope = null;
    
    public VoiceCommandDetector(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void setCustomCommand(@org.jetbrains.annotations.NotNull()
    java.lang.String command) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCustomCommand() {
        return null;
    }
    
    public final void setOnVoiceCommandListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> listener) {
    }
    
    public final void setOnErrorListener(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> listener) {
    }
    
    public final void startListening() {
    }
    
    public final void stopListening() {
    }
    
    public final boolean isCurrentlyListening() {
        return false;
    }
    
    private final android.speech.RecognitionListener createRecognitionListener() {
        return null;
    }
    
    private final void restartListening() {
    }
    
    public final void destroy() {
    }
}