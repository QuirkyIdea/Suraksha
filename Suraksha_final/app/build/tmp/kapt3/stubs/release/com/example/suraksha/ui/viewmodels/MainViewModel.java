package com.example.suraksha.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010 \u001a\u00020!J\u0006\u0010\"\u001a\u00020!J\u0006\u0010#\u001a\u00020!J\b\u0010$\u001a\u00020!H\u0014J\u000e\u0010%\u001a\u00020!2\u0006\u0010&\u001a\u00020\'J\u0006\u0010(\u001a\u00020!J\u000e\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020+J\u0006\u0010,\u001a\u00020!R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0013R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0013\u00a8\u0006-"}, d2 = {"Lcom/example/suraksha/ui/viewmodels/MainViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_currentLocation", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Landroid/location/Location;", "_timerState", "Lcom/example/suraksha/ui/viewmodels/TimerState;", "_uiState", "Lcom/example/suraksha/ui/viewmodels/MainUiState;", "contactsViewModel", "Lcom/example/suraksha/ui/viewmodels/ContactsViewModel;", "getContactsViewModel", "()Lcom/example/suraksha/ui/viewmodels/ContactsViewModel;", "currentLocation", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentLocation", "()Lkotlinx/coroutines/flow/StateFlow;", "emergencyContacts", "", "Lcom/example/suraksha/data/EmergencyContact;", "getEmergencyContacts", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "repository", "Lcom/example/suraksha/data/SurakshaRepository;", "timerState", "getTimerState", "uiState", "getUiState", "cancelTimer", "", "clearError", "completeOnboarding", "onCleared", "setDisguisedMode", "enabled", "", "startLocationUpdates", "startTimer", "minutes", "", "triggerSOS", "app_release"})
public final class MainViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.suraksha.data.SurakshaRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.suraksha.ui.viewmodels.ContactsViewModel contactsViewModel = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.suraksha.ui.viewmodels.MainUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.suraksha.ui.viewmodels.MainUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.suraksha.data.EmergencyContact>> emergencyContacts = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<android.location.Location> _currentLocation = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<android.location.Location> currentLocation = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.suraksha.ui.viewmodels.TimerState> _timerState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.suraksha.ui.viewmodels.TimerState> timerState = null;
    
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.suraksha.ui.viewmodels.ContactsViewModel getContactsViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.suraksha.ui.viewmodels.MainUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.suraksha.data.EmergencyContact>> getEmergencyContacts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<android.location.Location> getCurrentLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.suraksha.ui.viewmodels.TimerState> getTimerState() {
        return null;
    }
    
    public final void triggerSOS() {
    }
    
    public final void startLocationUpdates() {
    }
    
    public final void startTimer(int minutes) {
    }
    
    public final void cancelTimer() {
    }
    
    public final void setDisguisedMode(boolean enabled) {
    }
    
    public final void completeOnboarding() {
    }
    
    public final void clearError() {
    }
    
    @java.lang.Override()
    protected void onCleared() {
    }
}