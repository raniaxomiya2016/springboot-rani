package com.brianeno.swagger3.service;

import com.brianeno.swagger3.model.ChargeSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChargeSessionService {

    static List<ChargeSession> chargeSessions = new ArrayList<ChargeSession>();
    static long id = 0;

    public List<ChargeSession> findAll() {
        return chargeSessions;
    }

    public List<ChargeSession> findByTitleContaining(String vin) {
        return chargeSessions.stream().filter(chargeSession -> chargeSession.getVin().contains(vin)).toList();
    }

    public ChargeSession findById(long id) {
        return chargeSessions.stream().filter(chargeSession -> id == chargeSession.getId()).findAny().orElse(null);
    }

    public ChargeSession save(ChargeSession chargeSession) {
        // update Charging Session
        if (chargeSession.getId() != 0) {
            long _id = chargeSession.getId();

            for (int idx = 0; idx < chargeSessions.size(); idx++)
                if (_id == chargeSessions.get(idx).getId()) {
                    chargeSessions.set(idx, chargeSession);
                    break;
                }

            return chargeSession;
        }

        // create new charging session
        chargeSession.setId(++id);
        chargeSessions.add(chargeSession);
        return chargeSession;
    }

    public void deleteById(long id) {
        chargeSessions.removeIf(chargeSession -> id == chargeSession.getId());
    }

    public void deleteAll() {
        chargeSessions.removeAll(chargeSessions);
    }

    public List<ChargeSession> findByCompleted(boolean isCompleted) {
        return chargeSessions.stream().filter(chargeSession -> isCompleted == chargeSession.isCompleted()).toList();
    }
}
