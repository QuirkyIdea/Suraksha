package com.example.suraksha.ui.screens;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000@\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001af\u0010\u0007\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u000526\u0010\t\u001a2\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\r\u0012\u0013\u0012\u00110\u000b\u00a2\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0007\u001a\b\u0010\u0013\u001a\u00020\u0001H\u0007\u001a\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002\u001a\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u000e\u001a\u00020\u000bH\u0002\u00a8\u0006\u001b"}, d2 = {"ContactCard", "", "contact", "Lcom/example/suraksha/data/EmergencyContact;", "onEdit", "Lkotlin/Function0;", "onDelete", "ContactDialog", "onDismiss", "onSave", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "phoneNumber", "onPickContact", "ContactsScreen", "viewModel", "Lcom/example/suraksha/ui/viewmodels/ContactsViewModel;", "EmptyContactsState", "getContactFromUri", "context", "Landroid/content/Context;", "uri", "Landroid/net/Uri;", "isValidPhoneNumber", "", "app_debug"})
public final class ContactsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ContactsScreen(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.ui.viewmodels.ContactsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyContactsState() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ContactCard(@org.jetbrains.annotations.NotNull()
    com.example.suraksha.data.EmergencyContact contact, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ContactDialog(@org.jetbrains.annotations.Nullable()
    com.example.suraksha.data.EmergencyContact contact, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPickContact) {
    }
    
    private static final com.example.suraksha.data.EmergencyContact getContactFromUri(android.content.Context context, android.net.Uri uri) {
        return null;
    }
    
    private static final boolean isValidPhoneNumber(java.lang.String phoneNumber) {
        return false;
    }
}