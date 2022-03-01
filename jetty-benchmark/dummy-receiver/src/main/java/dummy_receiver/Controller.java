package dummy_receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private Logger log = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private void busy(int millis) {
        long startTime = System.currentTimeMillis();
        for (; System.currentTimeMillis() - startTime < millis; ) {
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> receiveMessage(@RequestBody String body) throws InterruptedException {
//        kafkaTemplate.send("benchmark", body);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
