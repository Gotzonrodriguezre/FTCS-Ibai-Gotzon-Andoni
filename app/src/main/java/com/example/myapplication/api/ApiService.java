package com.example.myapplication.api;

import com.example.myapplication.Modelo.Perfil;
import com.example.myapplication.Modelo.Producto;
import com.example.myapplication.Modelo.Albaran;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiService {

    // --- PERFILES ---

    // Obtener todos los perfiles (GET /api/perfiles)
    @GET("/api/perfiles")
    Call<List<Perfil>> getAllPerfiles();

    // Obtener un perfil por id (GET /api/perfiles/{id})
    @GET("/api/perfiles/{id}")
    Call<Perfil> getPerfilById(@Path("id") int id);

    // Actualizar un perfil por id (PUT /api/perfiles/{id})
    @PUT("/api/perfiles/{id}")
    Call<Void> actualizarPerfil(@Path("id") int id, @Body Perfil perfil);

    // Borrar un perfil por id (DELETE /api/perfiles/{id})
    @DELETE("/api/perfiles/{id}")
    Call<Void> borrarPerfil(@Path("id") int id);

    // Crear un nuevo perfil (POST /api/perfiles)
    @POST("/api/perfiles")
    Call<Perfil> crearPerfil(@Body Perfil perfil);


    // --- PRODUCTOS ---

    // Obtener todos los productos (GET /api/productos)
    @GET("/api/productos")
    Call<List<Producto>> getAllProductos();

    // Actualizar cantidad de producto (PUT /api/productos/{id})
    @PUT("/api/productos/{id}")
    Call<Producto> updateProductoCantidad(@Path("id") int id, @Body Producto producto);

    // Actualizar producto completo (PUT /api/productos/{id})
    @PUT("/api/productos/{id}")
    Call<Producto> actualizarProducto(@Path("id") int id, @Body Producto producto);

    // Crear un nuevo producto (POST /api/productos)
    @POST("/api/productos")
    Call<Producto> crearProducto(@Body Producto producto);

    // Borrar un producto por id (DELETE /api/productos/{id})
    @DELETE("/api/productos/{id}")
    Call<Void> eliminarProducto(@Path("id") int id);


    // --- ALBARANES ---

    // Obtener todos los albaranes (GET /api/albaranes)
    @GET("/api/albaranes")
    Call<List<Albaran>> getAllAlbaranes();

    // Crear un nuevo albarán (POST /api/albaranes)
    @POST("/api/albaranes")
    Call<Albaran> crearAlbaran(@Body Albaran albaran);
}