package br.com.bs.bsback.controller;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@RestController
@RequestMapping("/brawlers")
public class BsBackController {
	
	@GetMapping("/lista")
	public ResponseEntity<String> getListaBrawlers() {
		
		ResponseEntity<String> resposta = null;
		
        try {
        	
			MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017");	//Conexao com o MongoDB
			MongoDatabase database = mongoClient.getDatabase("bsraw");					//Apontamento da base
			MongoCollection<Document> collection = database.getCollection("brawlers");	//Colecao do mongo desejada

        	StringBuilder sb = new StringBuilder("[");	//Vai armazenar o JSON de resposta
        	
			//Iteracao para leitura de cada item da colecao
	        for(Document docFound : collection.find()) {
	        	sb.append(docFound.toJson());
	        	sb.append(",");	//Mantendo a estrutura do JSON
	        }

	        int lastIndexOf = sb.lastIndexOf(",");						//Mantendo a estrutura do JSON
	        sb.append(sb.toString().substring(0, lastIndexOf) + "]");	//Mantendo a estrutura do JSON

	        resposta = new ResponseEntity<String>(sb.toString(), HttpStatus.OK);	//Criando resposta OK
	        
		} catch (Exception e) {
			e.printStackTrace();	//Deve ser evitado, mas para estudos esta' ok
	        resposta = new ResponseEntity<String>("{}", HttpStatus.INTERNAL_SERVER_ERROR);	//Criando resposta ERROR
		}
        
        return resposta;
        
	}
	
}
