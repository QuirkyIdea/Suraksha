package com.example.suraksha.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a2\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\u001e\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a>\u0010\r\u001a\u00020\u00012\u0018\u0010\u000e\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\fH\u0007\u001a\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u000e\u0010\u0015\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\u0010\u001a\u0015\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u00a2\u0006\u0002\u0010\u001a\u001a\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u00a8\u0006\u001d"}, d2 = {"PermissionCategoryCard", "", "category", "Lcom/example/suraksha/utils/PermissionCategory;", "permissions", "", "Lcom/example/suraksha/utils/PermissionInfo;", "onPermissionClick", "Lkotlin/Function1;", "PermissionItem", "permission", "onClick", "Lkotlin/Function0;", "PermissionRequestScreen", "onRequestPermissions", "", "", "onOpenSettings", "onSkip", "getCategoryInfo", "Lcom/example/suraksha/ui/screens/CategoryInfo;", "getPermissionDisplayName", "getStatusColor", "Landroidx/compose/ui/graphics/Color;", "status", "Lcom/example/suraksha/utils/PermissionStatus;", "(Lcom/example/suraksha/utils/PermissionStatus;)J", "getStatusIcon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "app_debug"})
public final class PermissionRequestActivityKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PermissionRequestScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String[], kotlin.Unit> onRequestPermissions, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenSettings, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onSkip) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PermissionCategoryCard(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.utils.PermissionCategory category, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.suraksha.utils.PermissionInfo> permissions, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.suraksha.utils.PermissionInfo, kotlin.Unit> onPermissionClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PermissionItem(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.utils.PermissionInfo permission, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    @org.jetbrains.annotations.NotNull()
    public static final androidx.compose.ui.graphics.vector.ImageVector getStatusIcon(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.utils.PermissionStatus status) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    public static final long getStatusColor(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.utils.PermissionStatus status) {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final com.example.suraksha.ui.screens.CategoryInfo getCategoryInfo(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.utils.PermissionCategory category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String getPermissionDisplayName(@org.jetbrains.annotations.NotNull()
    java.lang.String permission) {
        return null;
    }
}