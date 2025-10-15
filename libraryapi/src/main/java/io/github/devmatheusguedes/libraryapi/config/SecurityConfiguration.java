package io.github.devmatheusguedes.libraryapi.config;

import io.github.devmatheusguedes.libraryapi.security.CustomUserDetailsService;
import io.github.devmatheusguedes.libraryapi.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(config ->{ aqui é onde pode-se customizar a tela de login
//        utilizando uma arquivo html dentro do projeto, caso o login seja um sucesso
////                    config.loginPage("/login.html")
//                            .successForwardUrl("/home.html");
//        o metodo sucessformurl chama a pagina de acordo com o indicado no campo url.
//                })
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->{
                    // a string /autores/** significa que para qualquer url precedida de autores
                    // seja aplicado tal regra.
                    //  a regra a .hasRole("ADMIN") significa que apenas os usuarios com a role admin podem fazer
                    // operações em autores
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");

                    // nesta parte, você pode restringirapenas algumas operações
                    // que no caso, a operação de salvar esta sendo restringida para apenas
                    // os administrsdores (admin)
                   //++ authorize.requestMatchers(HttpMethod.POST, "/livros/**").hasRole("ADMIN");

                    // A regra .hasAnyRole("USER", "ADMIN"); significa que tanto os usuarios quanto
                    // os daministradores podem fazer operações na url de livros
                    authorize.requestMatchers("/livros/**").hasAnyRole("USER", "ADMIN");
                    authorize.requestMatchers("/login/**").permitAll();
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll();
                    // a regra .authenticated() signica que, caso a aplicação tenha outras URLs,
                    // essa regra vai se aplicar a todas as que não foi especificado as regras aqui no authorize
                    // fazendo com que elas sejam autorizadas as operações
                    // +++ essa regra tem que ser a ultima regra fornecida +++
                    authorize.anyRequest().authenticated();
                })
                .build();
    }

    // decodificar/codificar e verficar as senhas
    @Bean
    public PasswordEncoder passwordEncoder(){
        // O BCryptPasswordEncoder é um algorirmo codificador de senha muito seguro
        // pois ele não permite que a decodificção da código gerado forneça a senha usada
        // ou seja, não existe caminho de volta.
        return new BCryptPasswordEncoder(10);
    }

    // criar detalhes de usuarios sem precisar de acesso ao banco de dados, por enquanto
    // este repositorio foi criado para fins de teste
    @Bean
    public UserDetailsService userDetailsService(UsuarioService usuarioService){

//        UserDetails user1 = User
//                .builder()
//                .username("user")
//                .password(passwordEncoder.encode("1234")) // senhas codificadas
//                .roles("USER")
//                .build();
//        UserDetails user2 = User
//                .builder()
//                .username("admin")
//                .password(passwordEncoder.encode("4321")) // senhas codificadas
//                .roles("ADMIN")
//                .build();


        return new CustomUserDetailsService(usuarioService);
    }
}
