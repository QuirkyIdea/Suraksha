package com.example.suraksha.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0002\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0003\u001a\u00020\u0001H\u0007\u001a.\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a\u0010\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0007\u001a3\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\b2\u0011\u0010\u0011\u001a\r\u0012\u0004\u0012\u00020\u00010\u000b\u00a2\u0006\u0002\b\u0012H\u0007\u001a<\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u001a$\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\b2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u00a8\u0006\u001b"}, d2 = {"EmergencyInfoCard", "", "PermissionStatusSection", "SettingsDivider", "SettingsItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "", "subtitle", "onClick", "Lkotlin/Function0;", "SettingsScreen", "mainViewModel", "Lcom/example/suraksha/ui/viewmodels/MainViewModel;", "SettingsSection", "description", "content", "Landroidx/compose/runtime/Composable;", "SettingsSwitch", "checked", "", "onCheckedChange", "Lkotlin/Function1;", "VoiceCommandSetting", "currentCommand", "onCommandChange", "app_debug"})
public final class SettingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SettingsScreen(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.ui.viewmodels.MainViewModel mainViewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsSection(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsSwitch(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, boolean checked, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onCheckedChange) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsItem(@org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SettingsDivider() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmergencyInfoCard() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void VoiceCommandSetting(@org.jetbrains.annotations.NotNull()
    java.lang.String currentCommand, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCommandChange) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PermissionStatusSection() {
    }
}