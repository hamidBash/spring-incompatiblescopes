# Enabling Cache 
###1- Add dependency in pom.xml

[//]: # (<dependency>)
      <groupId>com.tiddev</groupId>
      <artifactId>integration-cache</artifactId>
      <version>1.2</version>

[//]: # (</dependency>)

###2- Add EnableCaching Annotation in SpringBoot Application class
      @EnableCaching
###3- Add ComponentScan Annotation in AppConfig class 
      @ComponentScan(basePackages = "com.tiddev.integration.cache") 
###4- Inject ICacheService  in AppConfig class
      private final ICacheService cacheService;  

      public AppConfig(ICacheService cacheService) {
        this.cacheService = cacheService;
      }
###5-Define jpaCacheManager Bean in AppConfig class
      @Bean("jpaCacheManager")
      public CacheManager cacheManager() {
        List<JpaCache.JpaCacheBuilder> caches = new ArrayList<>();
        JpaCache.JpaCacheBuilder<Response> serviceCache = JpaCache.<Response>builder()
          .cacheName("**cach_name**")
          .ttlInSecond(TimeUnit.DAYS.toSeconds(15))
          .clazz(Response.class)
          .cacheService(cacheService);
        caches.add(serviceCache);
        return new JpaCacheManager(caches);
      }
###6- define Spring cache datasource and data table name in Application.yml
    spring:
      application:
        name: {fetch serviceId from gateway for related service}
      datasource:
        cache:
          enabled: true
          driverClassName: oracle.jdbc.OracleDriver
          jdbcUrl: jdbc:oracle:thin:@192.168.100.120:1521/gateway_dev
          username: CACHE
          password: CaChe*123
          dataModelVersion: v1
  ####For uat and prod define datasource config :
   ####UAT:
    spring:
      datasource:
        cache:
        enabled: true
        driverClassName: oracle.jdbc.OracleDriver
        jdbcUrl: jdbc:oracle:thin:@10.39.214.219:1521/gateway_uat
        username: CACHE
        password: CaChe*123
        dataModelVersion: v1
   ####PROD:
    spring:
      datasource:
        cache:
        enabled: true
        driverClassName: oracle.jdbc.OracleDriver
        jdbcUrl: jdbc:oracle:thin:@10.40.41.51:1521/gateway 
        username: CACHE
        password: CaChe*123
        dataModelVersion: v1
###7- Add CacheService Component in Service package
    @Component
    public class CacheService {
      public Boolean checkApigCache(HttpServletRequest request) {
        if (request.getHeader("Apig-Cache") == null) {
          System.out.println("HttpServletRequest:"+request);
            return true;
        }
        else if (request.getHeader("Apig-Cache") != null && !request.getHeader("Apig-Cache").equalsIgnoreCase("disabled")){
          System.out.println("HttpServletRequest:"+request);
          return true;
        }
        else
          return false;
      }
    }
###8- Add base package and within classes in dto package
  ![](D:\IdeaProjects\integration-sorena-customer\img.png)
  
  ##### Response class :
    public class Response<R> extends FollowUp implements Serializable {
      private static final long serialVersionUID = -930908642382128314L;
      private R response;
      private BusinessMessage message;
      public Response() {}

      public Response(R response) {this.response = response;}

      public Response(BusinessMessage message) {this.message = message;}

      public R getResponse() {return response;}

      public void setResponse(R response) {this.response = response;}

      public BusinessMessage getMessage() {return message;}

      public void setMessage(BusinessMessage message) {this.message = message;}

      @Override
      public String toString() {
        return "Response{" +
                "response=" + response +
                ", message=" + message +
                '}';
      }   
    }
  ##### BusinessMessage class :
    public class BusinessMessage implements Serializable {
    private static final long serialVersionUID = 4300523262119437798L;

      private Short code;
      private String text;

      public BusinessMessage() { }
      public BusinessMessage(Short code, String text) {
        this.code = code;
        this.text = text;
      }

      public Short getCode() { return code; }
      public void setCode(Short code) { this.code = code; }

      public String getText() { return text; }
      public void setText(String text) { this.text = text; }

      @Override
      public String toString() {
        return "BusinessMessage{" +
          "code=" + code +
          ", text='" + text + '\'' +
          '}';
      }
    }
###9- Add @Cacheable Annotaion with conditions on top of methode in service impeliment class :
        @Cacheable(cacheManager = "jpaCacheManager", cacheNames = "**cach_name**",
            key = "#key",
            condition = "#root.target.isCacheEnabled && @cacheService.checkApigCache(#httpServletRequest)",
            unless = "#result.response == null || #result.response.error != null")
        cacheNames : This cache name is the same as the cache name in the AppConfig class
        #key :  This key is generated by combining the request input parameters
###10- casting ResponseDto of methode in service to Response Class :
    public Response<GetResponseDto> -methodename- (---){
        .
        .
        .
        return new Response<>(getResponseDto);
     }

###11- In Controller class :
  #####a: inject ICacheService  :
    private final ICacheService cacheService;

     public ServiceController(ICacheService cacheService) {
       this.cacheService = cacheService;
     }
  #####b:  in methode of get or post Mapping :
   ######cast response Of Service to Response class : 
      Response<GetResponseDto> getResponseDto = serviceImplementorClass.methodeName(requestParam or requestObject,httpServletRequest);
    
   ###### for converting response of cache to response model of methode :  
      ResponseEntity.BodyBuilder responseEntity = ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON);
        if (getResponseDto.getUuid() != null) {
            responseEntity.header("Follow-Up", getResponseDto.getUuid());
        }
        String jsonString = new Gson().toJson(getResponseDto.getResponse());
        GetResponseDto response =  new Gson().fromJson(jsonString, GetResponseDto.class);
        return responseEntity.body(response);
    
###12 : create DataTable with Same name of ServiceId in Cache DataSource
   **create this cache table for each service in qa , uat and prod severs** 
   **for example : sorena-customer-service :**

    create sequence ISEQ$$_99241
    create table SORENA_CUSTOMER_SERVICE
    (
     ID               NUMBER     default "CACHE"."ISEQ$$_99241".nextval
     primary key,
     PROFILE          VARCHAR2(8),
     CACHENAME        VARCHAR2(32),
     DATAMODELVERSION VARCHAR2(16),
     KEY              VARCHAR2(128),
     RESPONSE         CLOB,
     UUID             VARCHAR2(36),
     TIMESTAMP        TIMESTAMP(6),
     HITS             NUMBER(13) default 1
    )


