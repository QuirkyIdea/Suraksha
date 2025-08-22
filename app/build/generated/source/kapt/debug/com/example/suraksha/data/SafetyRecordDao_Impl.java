package com.example.suraksha.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SafetyRecordDao_Impl implements SafetyRecordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SafetyRecord> __insertionAdapterOfSafetyRecord;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<SafetyRecord> __updateAdapterOfSafetyRecord;

  private final SharedSQLiteStatement __preparedStmtOfMarkRecordResolved;

  public SafetyRecordDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSafetyRecord = new EntityInsertionAdapter<SafetyRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `safety_records` (`id`,`type`,`latitude`,`longitude`,`timestamp`,`recordingPath`,`contactsNotified`,`isResolved`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SafetyRecord entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getType() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getType());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getLongitude());
        }
        final String _tmp = __converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        if (entity.getRecordingPath() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRecordingPath());
        }
        if (entity.getContactsNotified() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getContactsNotified());
        }
        final int _tmp_1 = entity.isResolved() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
      }
    };
    this.__updateAdapterOfSafetyRecord = new EntityDeletionOrUpdateAdapter<SafetyRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `safety_records` SET `id` = ?,`type` = ?,`latitude` = ?,`longitude` = ?,`timestamp` = ?,`recordingPath` = ?,`contactsNotified` = ?,`isResolved` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SafetyRecord entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getType() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getType());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(3);
        } else {
          statement.bindDouble(3, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(4);
        } else {
          statement.bindDouble(4, entity.getLongitude());
        }
        final String _tmp = __converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp);
        }
        if (entity.getRecordingPath() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getRecordingPath());
        }
        if (entity.getContactsNotified() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getContactsNotified());
        }
        final int _tmp_1 = entity.isResolved() ? 1 : 0;
        statement.bindLong(8, _tmp_1);
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfMarkRecordResolved = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE safety_records SET isResolved = 1 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertRecord(final SafetyRecord record,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSafetyRecord.insertAndReturnId(record);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateRecord(final SafetyRecord record,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSafetyRecord.handle(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markRecordResolved(final long recordId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkRecordResolved.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, recordId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkRecordResolved.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SafetyRecord>> getAllRecords() {
    final String _sql = "SELECT * FROM safety_records ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"safety_records"}, new Callable<List<SafetyRecord>>() {
      @Override
      @NonNull
      public List<SafetyRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfRecordingPath = CursorUtil.getColumnIndexOrThrow(_cursor, "recordingPath");
          final int _cursorIndexOfContactsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "contactsNotified");
          final int _cursorIndexOfIsResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "isResolved");
          final List<SafetyRecord> _result = new ArrayList<SafetyRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SafetyRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final LocalDateTime _tmpTimestamp;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __converters.fromTimestamp(_tmp);
            final String _tmpRecordingPath;
            if (_cursor.isNull(_cursorIndexOfRecordingPath)) {
              _tmpRecordingPath = null;
            } else {
              _tmpRecordingPath = _cursor.getString(_cursorIndexOfRecordingPath);
            }
            final String _tmpContactsNotified;
            if (_cursor.isNull(_cursorIndexOfContactsNotified)) {
              _tmpContactsNotified = null;
            } else {
              _tmpContactsNotified = _cursor.getString(_cursorIndexOfContactsNotified);
            }
            final boolean _tmpIsResolved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsResolved);
            _tmpIsResolved = _tmp_1 != 0;
            _item = new SafetyRecord(_tmpId,_tmpType,_tmpLatitude,_tmpLongitude,_tmpTimestamp,_tmpRecordingPath,_tmpContactsNotified,_tmpIsResolved);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<SafetyRecord>> getRecordsByType(final String type) {
    final String _sql = "SELECT * FROM safety_records WHERE type = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"safety_records"}, new Callable<List<SafetyRecord>>() {
      @Override
      @NonNull
      public List<SafetyRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfRecordingPath = CursorUtil.getColumnIndexOrThrow(_cursor, "recordingPath");
          final int _cursorIndexOfContactsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "contactsNotified");
          final int _cursorIndexOfIsResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "isResolved");
          final List<SafetyRecord> _result = new ArrayList<SafetyRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SafetyRecord _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final LocalDateTime _tmpTimestamp;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __converters.fromTimestamp(_tmp);
            final String _tmpRecordingPath;
            if (_cursor.isNull(_cursorIndexOfRecordingPath)) {
              _tmpRecordingPath = null;
            } else {
              _tmpRecordingPath = _cursor.getString(_cursorIndexOfRecordingPath);
            }
            final String _tmpContactsNotified;
            if (_cursor.isNull(_cursorIndexOfContactsNotified)) {
              _tmpContactsNotified = null;
            } else {
              _tmpContactsNotified = _cursor.getString(_cursorIndexOfContactsNotified);
            }
            final boolean _tmpIsResolved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsResolved);
            _tmpIsResolved = _tmp_1 != 0;
            _item = new SafetyRecord(_tmpId,_tmpType,_tmpLatitude,_tmpLongitude,_tmpTimestamp,_tmpRecordingPath,_tmpContactsNotified,_tmpIsResolved);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLatestUnresolvedRecord(final Continuation<? super SafetyRecord> $completion) {
    final String _sql = "SELECT * FROM safety_records WHERE isResolved = 0 ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SafetyRecord>() {
      @Override
      @Nullable
      public SafetyRecord call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfRecordingPath = CursorUtil.getColumnIndexOrThrow(_cursor, "recordingPath");
          final int _cursorIndexOfContactsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "contactsNotified");
          final int _cursorIndexOfIsResolved = CursorUtil.getColumnIndexOrThrow(_cursor, "isResolved");
          final SafetyRecord _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final LocalDateTime _tmpTimestamp;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __converters.fromTimestamp(_tmp);
            final String _tmpRecordingPath;
            if (_cursor.isNull(_cursorIndexOfRecordingPath)) {
              _tmpRecordingPath = null;
            } else {
              _tmpRecordingPath = _cursor.getString(_cursorIndexOfRecordingPath);
            }
            final String _tmpContactsNotified;
            if (_cursor.isNull(_cursorIndexOfContactsNotified)) {
              _tmpContactsNotified = null;
            } else {
              _tmpContactsNotified = _cursor.getString(_cursorIndexOfContactsNotified);
            }
            final boolean _tmpIsResolved;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsResolved);
            _tmpIsResolved = _tmp_1 != 0;
            _result = new SafetyRecord(_tmpId,_tmpType,_tmpLatitude,_tmpLongitude,_tmpTimestamp,_tmpRecordingPath,_tmpContactsNotified,_tmpIsResolved);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
