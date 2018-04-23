package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

public class MealRestControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL + "/" + MEAL1_ID))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(MEAL1))
        );
    }

    @Test
    public void testDelete() throws Exception {
        TestUtil.print(
                mockMvc.perform(delete(REST_URL + "/" + MEAL1_ID))
                        .andExpect(status().isNoContent()));
        List<Meal> actualMeals = mealService.getAll(AuthorizedUser.id());
        List<Meal> expectedMeals = new ArrayList<>(MEALS);
        expectedMeals.remove(MEAL1);
        assertMatch(actualMeals, expectedMeals);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(MEALS.toArray(new Meal[6])))
        );
    }

    @Test
    public void testCreate() throws Exception{
        Meal created = getCreated();
        TestUtil.print(
                mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(created)))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(contentJson(created))
        );
        created.setId(MEAL1_ID + 8);
        assertMatch(created, mealService.get(MEAL1_ID + 8, AuthorizedUser.id()));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
//        updated.setUser(userService.get(AuthorizedUser.id()));
        TestUtil.print(
                mockMvc.perform(put(REST_URL + "/" + MEAL1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                        .andDo(print())
                        .andExpect(status().isOk()));
        assertMatch(updated, mealService.get(MEAL1_ID, AuthorizedUser.id()));
    }

    @Test
    public void testGetBetween() throws Exception {
        TestUtil.print(
                mockMvc.perform(get(REST_URL + "/filter/date")
                        .param("start_d", "2015-05-30").param("start_t", "07:00")
                        .param("end_d", "2015-05-31").param("end_t", "11:00"))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(contentJson(
                                Arrays.asList(MEAL4, MEAL1).toArray(new Meal[2])
                        )));
    }
}
