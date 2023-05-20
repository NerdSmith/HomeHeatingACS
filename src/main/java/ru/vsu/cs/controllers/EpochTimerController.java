package ru.vsu.cs.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.models.dtos.BoilerDto;
import ru.vsu.cs.models.dtos.EpochTimerDto;
import ru.vsu.cs.services.BoilerService;
import ru.vsu.cs.services.EpochTimerService;
import ru.vsu.cs.utils.JsonSerializer;

import java.io.BufferedReader;
import java.io.IOException;

public class EpochTimerController extends HttpServlet {

    @Autowired
    private static EpochTimerService epochTimerService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String id = pathInfo.substring(1);
            try {
                int intId = Integer.parseInt(id);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                String json = JsonSerializer.serialize(epochTimerService.getById(intId));
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
                String json = JsonSerializer.serialize(epochTimerService.getAll());
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

        epochTimerService.saveNew(JsonSerializer.deserialize(json, EpochTimerDto.class));
        response.setStatus(201);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            String id = pathInfo.substring(1);
            try {
                int intId = Integer.parseInt(id);

                epochTimerService.deleteById(intId);
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

                epochTimerService.update(intId, JsonSerializer.deserialize(json, EpochTimerDto.class));

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
