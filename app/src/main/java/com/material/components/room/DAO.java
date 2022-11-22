package com.material.components.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.material.components.room.table.FavoriteEntity;
import com.material.components.room.table.NotificationEntity;

import java.util.List;

@Dao
public interface DAO {

    /* table notification transaction ----------------------------------------------------------- */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotification(NotificationEntity notification);

    @Query("DELETE FROM notification WHERE id = :id")
    void deleteNotification(long id);

    @Query("DELETE FROM notification")
    void deleteAllNotification();

    @Query("SELECT * FROM notification ORDER BY created_at DESC LIMIT :limit OFFSET :offset")
    List<NotificationEntity> getNotificationByPage(int limit, int offset);

    @Query("SELECT * FROM notification WHERE id = :id LIMIT 1")
    NotificationEntity getNotification(long id);

    @Query("SELECT COUNT(id) FROM notification WHERE read = 0")
    Integer getNotificationUnreadCount();

    @Query("SELECT COUNT(id) FROM notification")
    Integer getNotificationCount();

    /* table favorite transaction ----------------------------------------------------------- */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteEntity favorite);

    @Query("DELETE FROM favorite WHERE id_str = :id")
    void deleteFavorite(String id);

    @Query("SELECT COUNT(idInt) FROM favorite WHERE id_str LIKE :id")
    Integer isFavorite(String id);

    @Query("SELECT * FROM favorite ORDER BY created_at DESC")
    List<FavoriteEntity> getFavorites();

}
