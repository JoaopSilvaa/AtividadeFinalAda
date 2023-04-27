package br.ada.scheduleTasksTests;


import br.ada.schedule.DatabaseUtil;
import br.ada.schedule.task.Task;
import br.ada.schedule.task.TaskStatus;
import br.ada.schedule.user.User;
import com.google.gson.Gson;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public class ScheduleTests {

    private Gson gson = new Gson();
    private Task task = null;
    private User user = new User();
    private RequestSpecification request = RestAssured.given()
            .baseUri("http://localhost:8080/api/tasks")
            .contentType(ContentType.JSON);
    private Response response;
    @Given("I have task registered")
    public void registeredTask(DataTable data) throws SQLException {
        user.setName("William");
        user.setUsername("wllm");
        user.setPassword("123456");
        DatabaseUtil.insertUser(user);
        task = createTaskFromDataTable(data);
        DatabaseUtil.insertTask(task);
    }

    @Given("that I don't have a task registered")
    public void iDontHaveTaskRegistered(DataTable data) throws SQLException {
        task = createTaskFromDataTable(data);

        Task found = DatabaseUtil.findTaskById(task.getId());
        Assertions.assertNull(found);
    }
    @When("I search the task by id")
    public void searchById() {
        response = request.when().get("/tasks/" + task.getId());
        response.prettyPrint();
    }

    @When("I register a task")
    public void registerTask() {
        String jsonBody = gson.toJson(task);
        response = request.body(jsonBody).when().post("/tasks");
        response.prettyPrint();
    }

    @Then("I should found {string} task")
    public void foundTaskByTitle(String title) {
        String found = response.jsonPath().get("[0].title");
        Assertions.assertEquals(title, found);
    }
    @Then("I shouldn't found a task")
    public void shouldNotFoundAnyTask() throws  SQLException {
        Task found = DatabaseUtil.findTaskById(task.getId());
        Assertions.assertNull(found);
    }

    @Then("found the task in database")
    public void searchInDatabase() throws SQLException {
        Task found = DatabaseUtil.findTaskById(task.getId());
        Assertions.assertNotNull(found);
    }

    @And("The response should have status equals {int}")
    public void statusEquals(Integer status) {
        response.then().statusCode(status);
    }


    @And("apply contract validation")
    public void applyContractValidation() throws FileNotFoundException {
        InputStream file = new FileInputStream("src/test/resources/task-schema.json");
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchema(file));
    }

    @And("apply contract validation on list")
    public void applyContractValidationOnList() throws FileNotFoundException {
        InputStream file = new FileInputStream("src/test/resources/list-tasks-schema.json");
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchema(file));
    }

    private Task createTaskFromDataTable(DataTable data) {
        Task task = new Task();
        data.asMaps().forEach(it -> {
            String title = it.get("title");
            if (title == null) {
                title = RandomStringUtils.randomAlphabetic(10);
            }
            task.setTitle(title);
            String description = it.get("description");
            TaskStatus status = TaskStatus.OPEN;
            if (description != null) {
                task.setDescription(description);
            }
            String closedAt = it.get("closedAt");
            if (closedAt != null) {
                LocalDate date = LocalDate.parse(closedAt);
                task.setClosedAt(date);
            }
            task.setUser(user);
            task.setStatus(status);
        });
        return task;
    }

}
