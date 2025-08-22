package com.example.suraksha.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000V\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0007\u001a\u001e\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u0010\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\fH\u0007\u001a0\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u001a@\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00010\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u001e\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u001a\u001e\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\tH\u0007\u00a8\u0006\""}, d2 = {"EmergencyContactsStatus", "", "contacts", "", "Lcom/example/suraksha/data/EmergencyContact;", "HomeScreen", "mainViewModel", "Lcom/example/suraksha/ui/viewmodels/MainViewModel;", "onNavigateToFakeCall", "Lkotlin/Function0;", "LocationStatusCard", "location", "", "QuickActionButton", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "label", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "QuickActionsSection", "onFakeCall", "onTimer", "Lkotlin/Function1;", "", "onRecording", "SOSButton", "isTriggered", "", "onSOSTrigger", "TimerSection", "timerState", "Lcom/example/suraksha/ui/viewmodels/TimerState;", "onCancelTimer", "app_debug"})
public final class HomeScreenKt {
    
    @androidx.compose.runtime.Composable()
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.ui.viewmodels.MainViewModel mainViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToFakeCall) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LocationStatusCard(@org.jetbrains.annotations.NotNull()
    java.lang.String location) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SOSButton(boolean isTriggered, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSOSTrigger) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void QuickActionsSection(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.ui.viewmodels.MainViewModel mainViewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFakeCall, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onTimer, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRecording) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void QuickActionButton(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TimerSection(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.ui.viewmodels.TimerState timerState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancelTimer) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmergencyContactsStatus(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.suraksha.data.EmergencyContact> contacts) {
    }
}