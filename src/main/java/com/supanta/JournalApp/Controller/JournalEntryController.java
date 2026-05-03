// package com.supanta.JournalApp.Controller;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.supanta.JournalApp.Entity.JournalEntry;
// @RestController
// @RequestMapping("/journal")
// public class JournalEntryController {
//     private Map<Long, JournalEntry> journalEntries= new HashMap<>();
//     @GetMapping //localhost:8080/journal GET
//     public List<JournalEntry> getAll() { 
//         return new ArrayList<>(journalEntries.values());
//     }
//     @PostMapping //localhost:8080/journal POST
//     public boolean createEntry(@RequestBody JournalEntry myEntry) 
//     {
//         journalEntries.put(myEntry.getId(), myEntry);
//         return true;
//     }
//     @GetMapping("id/{myId}") //localhost:8080/journal/id/1 GET  
//     public JournalEntry getJournalEntryBYId(@PathVariable Long myId)
//     {
//         return journalEntries.get(myId);
//     }

//     @DeleteMapping("id/{myId}") //localhost:8080/journal/id/1 DELETE
//     public JournalEntry deleteJournalEntryBYId(@PathVariable Long myId)
//     {
//         return journalEntries.remove(myId);
        
//     }
//     @PutMapping("id/{myId}") //localhost:8080/journal/id/1 PUT
//     public JournalEntry updateJournalEntry(@PathVariable Long myId, @RequestBody JournalEntry myEntry)
//     {
//         journalEntries.put(myId, myEntry);
//         return myEntry;
//     }
// }
