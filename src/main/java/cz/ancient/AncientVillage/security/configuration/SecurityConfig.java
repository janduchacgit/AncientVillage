package cz.ancient.AncientVillage.security.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cz.ancient.AncientVillage.model.enums.Role;
//import cz.ancient.dao.mapper.UserDAO;
//import cz.ancient.model.enums.Role;

/**
 * SecurityConfig -
 *
 * @author Jan DUCHAC
 */
//@EnableWebMvc
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //
    //
    //   @Autowired
    //   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    //      auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    //   }

    @Autowired
    private DataSource dataSource;

//   @Autowired
//   private UserDAO userDAO;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        final String usersByUsernameQuery = "SELECT playername, password, enabled FROM user WHERE playername = ?";
        final String authoritiesByUsernameQuery = "SELECT playername, role FROM user WHERE playername = ?";

        //      userDAO.getUser()
        //      auth.jdbcAuthentication().dataSource(dataSource)
        //            .usersByUsernameQuery(
        //                  "select username,password, enabled from users where username=?")
        //            .authoritiesByUsernameQuery(
        //                  "select username, role from user_roles where username=?");

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(usersByUsernameQuery)
                .authoritiesByUsernameQuery(authoritiesByUsernameQuery);
        //
        //      auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
        //            .usersByUsernameQuery(userByUsernameQuery)
        //            .authoritiesByUsernameQuery(roleByMailQuery);
    }

//   @Autowired
//   public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//       final String usersByUsernameQuery = "SELECT playername, password, enabled FROM user WHERE playername = ?";
//       final String authoritiesByUsernameQuery = "SELECT playername, role FROM user WHERE playername = ?";
//       
//       auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
//               .usersByUsernameQuery(usersByUsernameQuery)
//               .authoritiesByUsernameQuery(authoritiesByUsernameQuery);
//   }


//    @Override
//    public void configure(WebSecurity web) throws Exception {
////            web.ignoring().antMatchers("/", "/index");
//        super.configure(web);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO game to #
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/*").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/registration/*").permitAll()
                .antMatchers("/view/**").authenticated()
                .antMatchers("/admin/**").hasRole(Role.ADMIN.name());
        //            .antMatchers("/", "/index").permitAll().anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login.html")
                .defaultSuccessUrl("/view/village.html", true)
//                .failureUrl("/login-error.html")
                //.failureUrl("/login?error");
//            http.formLogin()
//                  .loginPage("/login")
                .usernameParameter("username").passwordParameter("password");
        //            .permitAll();
//                  .failureUrl("/login");
        //
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html");

        //      http.csrf();

        //      http.authorizeRequests()
        //            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
        //            .and()
        //            .formLogin().loginPage("/game/login").failureUrl("/game/login?error")
        //            .usernameParameter("p_login").passwordParameter("p_password")
        //            .and()
        //            .logout().logoutSuccessUrl("/game/login?logout")
        //            .and()
        //            .exceptionHandling().accessDeniedPage("/game/403")
        //            .and()
        //            .csrf();


      /*
       http.formLogin()
        .failureUrl("/pages/loginFailed")
        .defaultSuccessUrl("/")
        .loginPage("/pages/login")
        .permitAll()
        .and()
        .logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/pages/logout"))
        .logoutSuccessUrl("/pages/login")
        .permitAll();
       */
//      http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());

        super.configure(http);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // TODO static AncientVillageApplication
        return new BCryptPasswordEncoder();
    }

//   @Override
//   protected void configure(final HttpSecurity http) throws Exception {
//      // ...
//
//   }

//   @Bean
//   public SessionRegistry sessionRegistry() {
//      return new SessionRegistryImpl();
//   }

//   @Bean
//   public ServletListenerRegistrationBean httpSessionEventPublisher() {
//      return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
//   }

}
