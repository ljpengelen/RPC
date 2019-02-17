package nl.kabisa.rpc.rest;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import nl.kabisa.rpc.protos.VectorProtos;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VectorControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @TestConfiguration
    class Configuration {

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().additionalMessageConverters(new ProtobufHttpMessageConverter());
        }
    }

    @Test
    public void generates_json_vectors() throws JSONException {
        var response = testRestTemplate.getForEntity("/json/vectors?seed={seed}&numberOfVectors={numberOfVectors}", String.class, 0, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(new JSONArray(
                "["
                + " {"
                + "     \"start\": {"
                + "         \"x\": 0.730967787376657,"
                + "         \"y\": 0.24053641567148587,"
                + "        \"z\": 0.6374174253501083"
                + "     },"
                + "     \"end\": {"
                + "         \"x\": 0.5504370051176339,"
                + "         \"y\": 0.5975452777972018,"
                + "         \"z\": 0.3332183994766498"
                + "     }"
                + " }"
                + "]"), new JSONArray(response.getBody()), true);
    }

    @Test
    public void generates_proto_vectors() {
        var response = testRestTemplate.getForEntity("/proto/vectors?seed={seed}&numberOfVectors={numberOfVectors}", VectorProtos.Vectors.class, 0, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        var start = VectorProtos.Point.newBuilder()
                .setX(0.730967787376657)
                .setY(0.24053641567148587)
                .setZ(0.6374174253501083)
                .build();
        var end = VectorProtos.Point.newBuilder()
                .setX(0.5504370051176339)
                .setY(0.5975452777972018)
                .setZ(0.3332183994766498)
                .build();
        var vector = VectorProtos.Vector.newBuilder()
                .setStart(start)
                .setEnd(end)
                .build();
        var vectors = VectorProtos.Vectors.newBuilder()
                .addVectors(vector)
                .build();

        assertEquals(vectors, response.getBody());
    }
}