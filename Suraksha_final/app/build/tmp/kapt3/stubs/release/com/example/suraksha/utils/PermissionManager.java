package com.example.suraksha.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0012\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0014\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eR\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\n\u0010\u0007\u00a8\u0006\u0017"}, d2 = {"Lcom/example/suraksha/utils/PermissionManager;", "", "()V", "CRITICAL_PERMISSIONS", "", "", "getCRITICAL_PERMISSIONS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "REQUIRED_PERMISSIONS", "getREQUIRED_PERMISSIONS", "getMissingCriticalPermissions", "", "context", "Landroid/content/Context;", "getMissingPermissions", "hasAllPermissions", "", "hasAudioPermission", "hasCriticalPermissions", "hasLocationPermission", "hasSmsPermission", "hasStoragePermission", "app_release"})
public final class PermissionManager {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] REQUIRED_PERMISSIONS = {"android.permission.SEND_SMS", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.RECORD_AUDIO", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.VIBRATE", "android.permission.POST_NOTIFICATIONS"};
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] CRITICAL_PERMISSIONS = {"android.permission.SEND_SMS", "android.permission.ACCESS_FINE_LOCATION"};
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
    public final java.lang.String[] getCRITICAL_PERMISSIONS() {
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
    
    public final boolean hasStoragePermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
}