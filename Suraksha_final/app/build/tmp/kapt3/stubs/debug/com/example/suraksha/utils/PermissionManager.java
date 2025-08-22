package com.example.suraksha.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0005H\u0002J\u000e\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u0005J\u0016\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0005J \u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u00120\f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010 \u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010!\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010#\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010$\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010%\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010&\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u0014J\u0016\u0010\'\u001a\u00020\u001f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0005R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\n\u0010\u0007R\u001d\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0010\u0010\u0007\u00a8\u0006("}, d2 = {"Lcom/example/suraksha/utils/PermissionManager;", "", "()V", "CRITICAL_PERMISSIONS", "", "", "getCRITICAL_PERMISSIONS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "OPTIONAL_PERMISSIONS", "getOPTIONAL_PERMISSIONS", "PERMISSION_DESCRIPTIONS", "", "getPERMISSION_DESCRIPTIONS", "()Ljava/util/Map;", "REQUIRED_PERMISSIONS", "getREQUIRED_PERMISSIONS", "getMissingCriticalPermissions", "", "context", "Landroid/content/Context;", "getMissingPermissions", "getPermissionCategory", "Lcom/example/suraksha/utils/PermissionCategory;", "permission", "getPermissionDescription", "getPermissionStatus", "Lcom/example/suraksha/utils/PermissionStatus;", "getPermissionsByCategory", "Lcom/example/suraksha/utils/PermissionInfo;", "hasAllPermissions", "", "hasAudioPermission", "hasCallPermission", "hasCriticalPermissions", "hasLocationPermission", "hasNotificationPermission", "hasSmsPermission", "hasSystemAlertWindowPermission", "shouldShowPermissionRationale", "app_debug"})
public final class PermissionManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] REQUIRED_PERMISSIONS = {"android.permission.SEND_SMS", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECORD_AUDIO", "android.permission.VIBRATE", "android.permission.POST_NOTIFICATIONS", "android.permission.CALL_PHONE"};
    @org.jetbrains.annotations.NotNull()
    private static final java.util.Map<java.lang.String, java.lang.String> PERMISSION_DESCRIPTIONS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] CRITICAL_PERMISSIONS = {"android.permission.SEND_SMS", "android.permission.ACCESS_FINE_LOCATION"};
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] OPTIONAL_PERMISSIONS = {"android.permission.RECORD_AUDIO", "android.permission.VIBRATE", "android.permission.POST_NOTIFICATIONS"};
    @org.jetbrains.annotations.NotNull()
    public static final com.example.suraksha.utils.PermissionManager INSTANCE = null;
    
    private PermissionManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getREQUIRED_PERMISSIONS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.String> getPERMISSION_DESCRIPTIONS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getCRITICAL_PERMISSIONS() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getOPTIONAL_PERMISSIONS() {
        return null;
    }
    
    public final boolean hasAllPermissions(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean hasCriticalPermissions(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getMissingPermissions(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getMissingCriticalPermissions(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    public final boolean hasSmsPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean hasLocationPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean hasAudioPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean hasNotificationPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean hasCallPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean hasSystemAlertWindowPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPermissionDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String permission) {
        return null;
    }
    
    public final boolean shouldShowPermissionRationale(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String permission) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.suraksha.utils.PermissionStatus getPermissionStatus(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String permission) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<com.example.suraksha.utils.PermissionCategory, java.util.List<com.example.suraksha.utils.PermissionInfo>> getPermissionsByCategory(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    private final com.example.suraksha.utils.PermissionCategory getPermissionCategory(java.lang.String permission) {
        return null;
    }
}