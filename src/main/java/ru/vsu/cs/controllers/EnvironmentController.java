package ru.vsu.cs.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.models.dtos.EnvironmentDto;
import ru.vsu.cs.services.EnvironmentService;
import ru.vsu.cs.utils.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;

public class EnvironmentController extends HttpServlet {

    @Autowired
    private static EnvironmentService environmentService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String id = pathInfo.substring(1);
            try {
                int intId = Integer.parseInt(id);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String json = JsonSerializer.serialize(environmentService.getById(intId));
                response.getWriter().write(json);
            }
            catch (Exception e) {
                response.sendError(500);
            }
        }
        else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            try {
                String json = JsonSerializer.serialize(environmentService.getAll());
                response.getWriter().write(json);
            } catch (Exception e) {
                response.sendError(500);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line.strip());
        }
        String json = sb.toString();

        environmentService.saveNew(JsonSerializer.deserialize(json, EnvironmentDto.class));
        response.setStatus(201);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String id = pathInfo.substring(1);
            try {
                int intId = Integer.parseInt(id);

                environmentService.deleteById(intId);
                response.setStatus(200);
            }
            catch (Exception e) {
                response.sendError(500);
            }
        }
        else {
            response.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String id = pathInfo.substring(1);
            try {
                int intId = Integer.parseInt(id);

                StringBuilder sb = new StringBuilder();
                BufferedReader reader = request.getReader();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line.strip());
                }
                String json = sb.toString();

                environmentService.update(intId, JsonSerializer.deserialize(json, EnvironmentDto.class));

                response.setStatus(200);
            }
            catch (Exception e) {
                response.sendError(500);
            }
        }
        else {
            response.setStatus(500);
        }
    }
}
