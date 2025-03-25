package com.example.miprimeraaplicacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tienda";
    private static final int DATABASE_VERSION = 1;
    // Se crea la tabla productos con los campos: idProducto, codigo, descripcion, marca, presentacion, precio, foto
    private static final String SQLdb = "CREATE TABLE productos (" +
            "idProducto INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "codigo TEXT, " +
            "descripcion TEXT, " +
            "marca TEXT, " +
            "presentacion TEXT, " +
            "precio REAL, " +
            "foto TEXT)";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLdb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la estructura de la base de datos si es necesario
    }

    public String administrar_productos(String accion, String[] datos) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String mensaje = "ok", sql = "";
            switch (accion) {
                case "agregar":
                    sql = "INSERT INTO productos (codigo, descripcion, marca, presentacion, precio, foto) VALUES ('"
                            + datos[1] + "', '" + datos[2] + "', '" + datos[3] + "', '" + datos[4] + "', " + datos[5] + ", '" + datos[6] + "')";
                    break;
                case "modificar":
                    sql = "UPDATE productos SET codigo = '" + datos[1] +
                            "', descripcion = '" + datos[2] +
                            "', marca = '" + datos[3] +
                            "', presentacion = '" + datos[4] +
                            "', precio = " + datos[5] +
                            ", foto = '" + datos[6] +
                            "' WHERE idProducto = " + datos[0];
                    break;
                case "eliminar":
                    sql = "DELETE FROM productos WHERE idProducto = " + datos[0];
                    break;
            }
            db.execSQL(sql);
            db.close();
            return mensaje;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Cursor lista_productos() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM productos", null);
    }
}

