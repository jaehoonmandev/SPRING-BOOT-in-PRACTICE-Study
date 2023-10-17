package com.manning.sbip.ch07;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.manning.sbip.ch07.model.Course;
import com.manning.sbip.ch07.service.CourseService;


@SpringBootTest //모든 스프링 부트 빈을 생성, 컨텍스트를 포함하는 스프링 부트 환경을 사용
@AutoConfigureMockMvc// MockMVC 프레임워크 자동 구성
@ExtendWith(SpringExtension.class) // JUnit5의 주피터 프로그래밍 모델과 스프링 테스트 컨텍스트 프레임 워크를 함께 사용할 수 있다.
class Ch07ApplicationTest {
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc; // MockMvc 인스턴스 주입.

    //새로운 과정을 담는 테스트
    @Test
    public void testPostCourse() throws Exception {
        // Course 객체 빌딩.
        Course course = Course.builder()
                .name("Rapid Spring Boot Application Development")
                .category("Spring")
                .rating(5)
                .description("Rapid Spring Boot Application Development").build();
        //JSON 직렬화에 사용할 ObjecMapper 인스턴스
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/courses/") // POSt로 /courses/ 호출
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(course))) // JSON 직렬화
                .andDo(print()) // 테스트 진행 과정을 콘솔에 출력.
                //andExpect로 여러 테스트 조건을 확인
                //jsonPath()를 사용하여 본문에 담겨있는 JSON 내용을 검증.
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Rapid Spring Boot Application Development"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(status().isCreated()).andReturn().getResponse(); // HTTP 201 Created가 반환되는지.

        Integer id = JsonPath.parse(response.getContentAsString()).read("$.id"); // 생성이 완료된 response 응답에서 id를 추출.
        assertNotNull(courseService.getCourseById(id));

    }

    // 조회 하여 실제로 저장되었는지까지 확인.
    @Test
    public void testRetrieveCourse() throws Exception {
        Course course = Course.builder()
                .name("Rapid Spring Boot Application Development")
                .category("Spring")
                .rating(5)
                .description("Rapid Spring Boot Application Development").build();
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/courses/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(course)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Rapid Spring Boot Application Development"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(status().isCreated()).andReturn().getResponse();
        Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");

        mockMvc.perform(get("/courses/{id}",id))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Rapid Spring Boot Application Development"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(status().isOk());

    }

    //존재하지 않는 과정 조회
    @Test
    public void testInvalidCouseId() throws Exception {
        mockMvc.perform(get("/courses/{id}",100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //존재하지 않는 과정 업데이트
    @Test
    public void testUpdateCourse() throws Exception {
        Course course = Course.builder()
                .name("Rapid Spring Boot Application Development")
                .category("Spring")
                .rating(3)
                .description("Rapid Spring Boot Application Development").build();
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/courses/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(course)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Rapid Spring Boot Application Development"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(3))
                .andExpect(status().isCreated()).andReturn().getResponse();
        Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");

        Course updatedCourse = Course.builder()
                .name("Rapid Spring Boot Application Development")
                .category("Spring")
                .rating(5)
                .description("Rapid Spring Boot Application Development").build();

        mockMvc.perform(put("/courses/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedCourse)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Rapid Spring Boot Application Development"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(status().isOk());

    }
    //존재하지 않는 과정 삭제
    @Test
    public void testDeleteCourse() throws Exception {
        Course course = Course.builder()
                .name("Rapid Spring Boot Application Development")
                .category("Spring")
                .rating(5)
                .description("Rapid Spring Boot Application Development").build();
        ObjectMapper objectMapper = new ObjectMapper();

        MockHttpServletResponse response = mockMvc.perform(post("/courses/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(course)))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(5)))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andExpect(jsonPath("$.name").value("Rapid Spring Boot Application Development"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(status().isCreated()).andReturn().getResponse();
        Integer id = JsonPath.parse(response.getContentAsString()).read("$.id");

        mockMvc.perform(delete("/courses/{id}", id))
                .andDo(print())
                .andExpect(status().isOk());

    }
}