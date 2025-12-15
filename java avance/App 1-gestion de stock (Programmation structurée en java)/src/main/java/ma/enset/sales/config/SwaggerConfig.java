package ma.enset.sales.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZAROUATI Ayoub
 */

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("ayoub.zerouati@gmail.com");
        contact.setName("Ayoub");
        contact.setUrl("https://www.swagger.ma");
        License mitLicense = new License().name("MIT License").url("https://opensource.org/licenses/MIT");
        Info info = new Info().title("Api Carrefour").version("1.0.0").contact(contact)
                .description("Explore and interact with our API using this Swagger documentation. "
                        + "This API provides a comprehensive set of endpoints for managing various aspects of our system. "
                        + "From user authentication to data retrieval and updates, this documentation outlines the details of each API operation, "
                        + "including request and response formats. Feel free to test API calls directly from this Swagger UI to better understand how our services work. "
                        + "If you have any questions or need further assistance, please refer to our support documentation or contact our technical support team.")
                .termsOfService("").license(mitLicense);

        return new OpenAPI()
                .info(info);
    }

    private GroupedOpenApi group(String... paths) {
        return GroupedOpenApi.builder()
                .group("99- ALL")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    GroupedOpenApi all() {
        return group("/**");
    }
}
