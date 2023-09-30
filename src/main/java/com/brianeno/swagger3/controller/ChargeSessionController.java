package com.brianeno.swagger3.controller;

import com.brianeno.swagger3.model.ChargeSession;
import com.brianeno.swagger3.service.ChargeSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Charge Session", description = "Charge Management management APIs")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ChargeSessionController {
    @Autowired
    ChargeSessionService chargeSessionService;

    @Operation(summary = "Create a new Charge Session", tags = {"chargesessions", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = ChargeSession.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/chargesession")
    public ResponseEntity<ChargeSession> createChargingSession(@RequestBody ChargeSession chargeSession) {
        try {
            ChargeSession _chargeSession = chargeSessionService
                    .save(new ChargeSession(chargeSession.getVin(), chargeSession.getWattage(), chargeSession.isCompleted()));
            return new ResponseEntity<>(_chargeSession, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Retrieve all Charging Sessions", tags = {"chargesessions", "get", "filter"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ChargeSession.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "There are no Charging Sessions", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/chargesession")
    public ResponseEntity<List<ChargeSession>> getAllChargeSession(@RequestParam(required = false) String title) {
        try {
            List<ChargeSession> chargeSessions = new ArrayList<>();

            if (title == null)
                chargeSessions.addAll(chargeSessionService.findAll());
            else
                chargeSessions.addAll(chargeSessionService.findByTitleContaining(title));

            if (chargeSessions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(chargeSessions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            summary = "Retrieve a Charge Session by Id",
            description = "Get a Charge Session object by specifying its id. The response is Charge Session object with id, title, description and published status.",
            tags = {"chargesession", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ChargeSession.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/chargesession/{id}")
    public ResponseEntity<ChargeSession> getChargingSessionById(@PathVariable("id") long id) {
        ChargeSession chargeSession = chargeSessionService.findById(id);

        if (chargeSession != null) {
            return new ResponseEntity<>(chargeSession, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a Charge Session by Id", tags = {"chargesession", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ChargeSession.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})})
    @PutMapping("/chargesession/{id}")
    public ResponseEntity<ChargeSession> updateChargingSession(@PathVariable("id") long id, @RequestBody ChargeSession chargeSession) {
        ChargeSession _chargeSession = chargeSessionService.findById(id);

        if (_chargeSession != null) {
            _chargeSession.setVin(chargeSession.getVin());
            _chargeSession.setWattage(chargeSession.getWattage());
            _chargeSession.setCompleted(chargeSession.isCompleted());
            return new ResponseEntity<>(chargeSessionService.save(_chargeSession), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a Charge Session by Id", tags = {"chargesession", "delete"})
    @ApiResponses({@ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/chargesession/{id}")
    public ResponseEntity<HttpStatus> deleteChargingSession(@PathVariable("id") long id) {
        try {
            chargeSessionService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete all Charge Sessions", tags = {"chargesession", "delete"})
    @ApiResponses({@ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("/chargesession")
    public ResponseEntity<HttpStatus> deleteAllChargingSessions() {
        try {
            chargeSessionService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "Retrieve all completed Charge Session", tags = {"chargesession", "get", "filter"})
    @GetMapping("/chargesession/completed")
    public ResponseEntity<List<ChargeSession>> findByPublished() {
        try {
            List<ChargeSession> chargeSessions = chargeSessionService.findByCompleted(true);

            if (chargeSessions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(chargeSessions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
