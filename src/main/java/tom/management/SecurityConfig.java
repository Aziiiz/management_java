//package tom.management;
//
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//@Override
//public void SecurityConfig(HttpSecurity http) throws Exception {
//  http
//      .authorizeRequests()
//      .antMatchers("/resources/**").permitAll()
//      .antMatchers("/login*").permitAll()
//      .anyRequest().authenticated()
//      .and().formLogin().loginPage("/login");
//}