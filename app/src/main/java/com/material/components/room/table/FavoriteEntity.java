package com.material.components.room.table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.material.components.adapter.MnAdapter.Item;
import com.material.components.model.MnType;

import java.io.Serializable;

/*
 * To save favorite
 */

@Entity(tableName = "favorite")
public class FavoriteEntity implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id_str")
    public String id_str;

    @ColumnInfo(name = "idInt")
    public int idInt;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "parent")
    public String parent;

    @ColumnInfo(name = "created_at")
    public Long created_at;

    public FavoriteEntity() {
    }

    @Ignore
    public FavoriteEntity(int id, String parent, String title, Long created_at) {
        this.idInt = id;
        this.title = title;
        this.parent = parent;
        this.created_at = created_at;
        this.id_str = this.parent + " | " + this.title;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public int getIdInt() {
        return idInt;
    }

    public void setIdInt(int idInt) {
        this.idInt = idInt;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Item getOriginal() {
        Item item = new Item(idInt, parent, title, MnType.SUB, null);
        item.Id_str = id_str;
        return item;
    }
}
