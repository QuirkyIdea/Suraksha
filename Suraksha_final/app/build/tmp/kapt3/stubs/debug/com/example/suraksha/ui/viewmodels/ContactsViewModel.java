package com.example.suraksha.ui.viewmodels;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015J\u0006\u0010\u0017\u001a\u00020\u0013J\u0006\u0010\u0018\u001a\u00020\u0013J\u000e\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u000bJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0016\u001a\u00020\u0015H\u0002J\u000e\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u000bR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\r\u00a8\u0006\u001e"}, d2 = {"Lcom/example/suraksha/ui/viewmodels/ContactsViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/suraksha/ui/viewmodels/ContactsUiState;", "emergencyContacts", "Lkotlinx/coroutines/flow/StateFlow;", "", "Lcom/example/suraksha/data/EmergencyContact;", "getEmergencyContacts", "()Lkotlinx/coroutines/flow/StateFlow;", "repository", "Lcom/example/suraksha/data/SurakshaRepository;", "uiState", "getUiState", "addContact", "", "name", "", "phoneNumber", "clearError", "clearSuccess", "deleteContact", "contact", "isValidPhoneNumber", "", "updateContact", "app_debug"})
public final class ContactsViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.suraksha.data.SurakshaRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.suraksha.ui.viewmodels.ContactsUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.suraksha.ui.viewmodels.ContactsUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.suraksha.data.EmergencyContact>> emergencyContacts = null;
    
    public ContactsViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.suraksha.ui.viewmodels.ContactsUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.suraksha.data.EmergencyContact>> getEmergencyContacts() {
        return null;
    }
    
    public final void addContact(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String phoneNumber) {
    }
    
    public final void updateContact(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.data.EmergencyContact contact) {
    }
    
    public final void deleteContact(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.data.EmergencyContact contact) {
    }
    
    public final void clearError() {
    }
    
    public final void clearSuccess() {
    }
    
    private final boolean isValidPhoneNumber(java.lang.String phoneNumber) {
        return false;
    }
}