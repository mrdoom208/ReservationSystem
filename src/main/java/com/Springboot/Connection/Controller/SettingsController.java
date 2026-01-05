package com.Springboot.Connection.Controller;

import com.Springboot.Connection.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    // Update auto-cancel time
    @PostMapping("/auto-cancel")
    public ResponseEntity<String> updateAutoCancelTime(@RequestParam long minutes) {
        settingsService.setAutoCancelMinutes(minutes);
        return ResponseEntity.ok("Auto-cancel time updated to " + minutes + " minutes.");
    }

    // Get current auto-cancel time
    @GetMapping("/auto-cancel")
    public long getAutoCancelTime() {
        return settingsService.getAutoCancelMinutes();
    }

    @PostMapping("/auto-delete")
    public ResponseEntity<String> updateAutoDeleteMonths(@RequestParam int months) {
        settingsService.setAutoDeleteMonths(months);
        return ResponseEntity.ok("Auto-delete set to " + months + " month(s).");
    }

    // Get auto-delete months
    @GetMapping("/auto-delete")
    public int getAutoDeleteMonths() {
        return settingsService.getAutoDeleteMonths();
    }
}
