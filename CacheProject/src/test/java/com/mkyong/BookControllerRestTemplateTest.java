package com.mkyong;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/*@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
*/public class BookControllerRestTemplateTest {
	/*
	 * private static final ObjectMapper om = new ObjectMapper();
	 * 
	 * @Autowired private TestRestTemplate restTemplate;
	 * 
	 * @MockBean private EmployeeRepository mockRepository;
	 * 
	 * @Before public void init() { Employee book = new Employee(1L, "Book Name",
	 * "Mkyong"); when(mockRepository.findById(1L)).thenReturn(Optional.of(book)); }
	 * 
	 * @Test public void find_bookId_OK() throws JSONException {
	 * 
	 * String expected = "{id:1,name:\"Book Name\",author:\"Mkyong\",price:9.99}";
	 * 
	 * ResponseEntity<String> response = restTemplate.getForEntity("/books/1",
	 * String.class);
	 * 
	 * assertEquals(HttpStatus.OK, response.getStatusCode());
	 * assertEquals(MediaType.APPLICATION_JSON_UTF8,
	 * response.getHeaders().getContentType());
	 * 
	 * JSONAssert.assertEquals(expected, response.getBody(), false);
	 * 
	 * verify(mockRepository, times(1)).findById(1L);
	 * 
	 * }
	 * 
	 * @Test public void find_allBook_OK() throws Exception {
	 * 
	 * List<Employee> books = Arrays.asList( new Employee(1L, "Book A", "Ah Pig"),
	 * new Employee(2L, "Book B", "Ah Dog"));
	 * 
	 * when(mockRepository.findAll()).thenReturn(books);
	 * 
	 * String expected = om.writeValueAsString(books);
	 * 
	 * ResponseEntity<String> response = restTemplate.getForEntity("/books",
	 * String.class);
	 * 
	 * assertEquals(HttpStatus.OK, response.getStatusCode());
	 * JSONAssert.assertEquals(expected, response.getBody(), false);
	 * 
	 * verify(mockRepository, times(1)).findAll(); }
	 * 
	 * @Test public void find_bookIdNotFound_404() throws Exception {
	 * 
	 * String expected =
	 * "{status:404,error:\"Not Found\",message:\"Book id not found : 5\",path:\"/books/5\"}"
	 * ;
	 * 
	 * ResponseEntity<String> response = restTemplate.getForEntity("/books/5",
	 * String.class);
	 * 
	 * assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	 * JSONAssert.assertEquals(expected, response.getBody(), false);
	 * 
	 * }
	 * 
	 * @Test public void save_book_OK() throws Exception {
	 * 
	 * Employee newBook = new Employee(1L, "Spring Boot Guide", "mkyong");
	 * when(mockRepository.save(any(Employee.class))).thenReturn(newBook);
	 * 
	 * String expected = om.writeValueAsString(newBook);
	 * 
	 * ResponseEntity<String> response = restTemplate.postForEntity("/books",
	 * newBook, String.class);
	 * 
	 * assertEquals(HttpStatus.CREATED, response.getStatusCode());
	 * JSONAssert.assertEquals(expected, response.getBody(), false);
	 * 
	 * verify(mockRepository, times(1)).save(any(Employee.class));
	 * 
	 * }
	 * 
	 * @Test public void update_book_OK() throws Exception {
	 * 
	 * Employee updateBook = new Employee(1L, "ABC", "mkyong");
	 * when(mockRepository.save(any(Employee.class))).thenReturn(updateBook);
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity<String> entity
	 * = new HttpEntity<>(om.writeValueAsString(updateBook), headers);
	 * 
	 * ResponseEntity<String> response = restTemplate.exchange("/books/1",
	 * HttpMethod.PUT, entity, String.class);
	 * 
	 * assertEquals(HttpStatus.OK, response.getStatusCode());
	 * JSONAssert.assertEquals(om.writeValueAsString(updateBook),
	 * response.getBody(), false);
	 * 
	 * verify(mockRepository, times(1)).findById(1L); verify(mockRepository,
	 * times(1)).save(any(Employee.class));
	 * 
	 * }
	 * 
	 * @Test public void patch_bookAuthor_OK() {
	 * 
	 * when(mockRepository.save(any(Employee.class))).thenReturn(new Employee());
	 * String patchInJson = "{\"author\":\"ultraman\"}";
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity<String> entity
	 * = new HttpEntity<>(patchInJson, headers);
	 * 
	 * ResponseEntity<String> response = restTemplate.exchange("/books/1",
	 * HttpMethod.PUT, entity, String.class);
	 * 
	 * assertEquals(HttpStatus.OK, response.getStatusCode());
	 * 
	 * verify(mockRepository, times(1)).findById(1L); verify(mockRepository,
	 * times(1)).save(any(Employee.class));
	 * 
	 * }
	 * 
	 * @Test public void patch_bookPrice_405() throws JSONException {
	 * 
	 * String expected =
	 * "{status:405,error:\"Method Not Allowed\",message:\"Field [price] update is not allow.\"}"
	 * ;
	 * 
	 * String patchInJson = "{\"price\":\"99.99\"}";
	 * 
	 * HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_JSON); HttpEntity<String> entity
	 * = new HttpEntity<>(patchInJson, headers);
	 * 
	 * ResponseEntity<String> response = restTemplate.exchange("/books/1",
	 * HttpMethod.PATCH, entity, String.class);
	 * 
	 * assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
	 * JSONAssert.assertEquals(expected, response.getBody(), false);
	 * 
	 * verify(mockRepository, times(1)).findById(1L); verify(mockRepository,
	 * times(0)).save(any(Employee.class)); }
	 * 
	 * @Test public void delete_book_OK() {
	 * 
	 * doNothing().when(mockRepository).deleteById(1L);
	 * 
	 * HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
	 * ResponseEntity<String> response = restTemplate.exchange("/books/1",
	 * HttpMethod.DELETE, entity, String.class);
	 * 
	 * assertEquals(HttpStatus.OK, response.getStatusCode());
	 * 
	 * verify(mockRepository, times(1)).deleteById(1L); }
	 * 
	 * private static void printJSON(Object object) { String result; try { result =
	 * om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	 * System.out.println(result); } catch (JsonProcessingException e) {
	 * e.printStackTrace(); } }
	 */
}
