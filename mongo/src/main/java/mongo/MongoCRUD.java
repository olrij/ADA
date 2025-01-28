package mongo;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import java.util.List;

public class MongoCRUD {
	private static final String CONNECTION_STRING = "mongodb://localhost:27017/";
	private static final String DATABASE_NAME = "prueba";
	private static final String COLLECTION_NAME = "personas";
	private static MongoCollection<Document> collection;

	public static void main(String[] args) {
		try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
			// Inicializar la colecci�n
			MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
			collection = database.getCollection(COLLECTION_NAME);
			
			
			//Preparamos las personas a insertar
			Document nuevaPersona1 = new Document("nombre", "Juan P�rez")
			        .append("edad", 32)
			        .append("telefonos", Arrays.asList(123456789, 987654321))
			        .append("titulos", Arrays.asList(
			                new Document("nombre", "Ingenier�a en Sistemas").append("fecha", "2015-06-20"),
			                new Document("nombre", "Certificaci�n AWS").append("fecha", "2020-10-15")
			        ))
			        .append("aficiones", Arrays.asList("programar", "leer", "viajar"))
			        .append("fechaNacimiento", "1990-05-15");
			
			Document nuevaPersona2 = new Document("nombre", "Mar�a L�pez")
			        .append("edad", 28)
			        .append("telefonos", Arrays.asList(111222333, 444555666))
			        .append("titulos", Arrays.asList(
			                new Document("nombre", "Licenciatura en Dise�o Gr�fico").append("fecha", "2016-12-01"),
			                new Document("nombre", "Certificaci�n en UX/UI").append("fecha", "2021-07-18")
			        ))
			        .append("aficiones", Arrays.asList("fotograf�a", "arte", "correr"))
			        .append("fechaNacimiento", "1995-03-10");
			
			// Insertamos las dos personas
			crearPersona(nuevaPersona1);
			crearPersona(nuevaPersona2);
			
			// Consultamos los registros
			leerPersonas();
			leerPersonaPorNombre("Juan P�rez");
			
			// Actualizamos una persona y eliminamos otra
			actualizarPersona("Juan P�rez", 33);		
			eliminarPersona("Mar�a L�pez");
			
			// Comprobamos los cambios con la consulta con proyecci�n
			leerPersonasConProyeccion();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Insertar persona
	public static void crearPersona(Document nuevaPersona) {


		collection.insertOne(nuevaPersona);
		System.out.println("Documento creado: " + nuevaPersona.toJson());
	}

	// Consultar todas las personas
	public static void leerPersonas() {
		List<Document> personas = collection.find().into(new java.util.ArrayList<>());
		System.out.println("\nDocumentos en la colecci�n:");
		for (Document persona : personas) {
			System.out.println(persona.toJson());
		}
	}

	// Buscar personas por nombre
	public static void leerPersonaPorNombre(String nombre) {
		Document persona = collection.find(Filters.eq("nombre", nombre)).first();
		if (persona != null) {
			System.out.println("\nDocumento encontrado:");
			System.out.println(persona.toJson());
		} else {
			System.out.println("\nNo se encontr� un documento con el nombre: " + nombre);
		}
	}

	// Consulta en la que se realiza una proyecci�n. Se ve s�lo el nombre y la edad
	public static void leerPersonasConProyeccion() {
		try (MongoCursor<Document> cursor = collection.find()
				.projection(new Document("nombre", 1).append("edad", 1).append("_id", 0)).iterator()) {

			System.out.println("\nDocumentos en la colecci�n (solo 'nombre' y 'edad'):");
			while (cursor.hasNext()) {
				Document persona = cursor.next();
				System.out.println(persona.toJson());
			}
		} catch (Exception e) {
			System.err.println("Error al leer los documentos con proyecci�n: " + e.getMessage());
		}
	}

	// Actualizar 
	public static void actualizarPersona(String nombre, int nuevaEdad) {
		Bson filtro = Filters.eq("nombre", nombre);
		Bson actualizacion = Updates.set("edad", nuevaEdad);
		collection.updateOne(filtro, actualizacion);
		System.out.println("\nDocumento actualizado: Edad cambiada a " + nuevaEdad);
	}

	// Eliminar una persona
	public static void eliminarPersona(String nombre) {
		Bson filtro = Filters.eq("nombre", nombre);
		collection.deleteOne(filtro);
		System.out.println("\nDocumento eliminado con el nombre: " + nombre);
	}
}
