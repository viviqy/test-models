package com.fairychar.security.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Datetime: 2021/3/29 17:30 <br>
 *
 * @author chiyo <br>
 * @since 1.0
 */
@NoArgsConstructor
@Data
public class RemoteUserDTO {

    /**
     * appId : gxdj
     * timeStamp : 2021-03-29 17:29:09
     * result : 1
     * data : {"user":{"id":"275314570317438976","isdel":0,"parentId":"2","userAccount":"91510106MA68YTDC0C","userName":"","phone":"18030581373","userLabel":"企业上云","labelCode":"企业上云","email":"75095236@qq.com","userType":"9","cardNum":"","sort":0,"status":0,"entNum":"91510106MA68YTDC0C","entContacts":"何仕明","entName":"成都煜晔建材有限公司","entAreaCode":"510106","createTime":"2021-03-29 17:21:49","modifyTime":"2021-03-29 17:21:49"},"status":true}
     */

    @JsonProperty("appId")
    private String appId;
    @JsonProperty("timeStamp")
    private String timeStamp;
    @JsonProperty("result")
    private Integer result;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        /**
         * user : {"id":"275314570317438976","isdel":0,"parentId":"2","userAccount":"91510106MA68YTDC0C","userName":"","phone":"18030581373","userLabel":"企业上云","labelCode":"企业上云","email":"75095236@qq.com","userType":"9","cardNum":"","sort":0,"status":0,"entNum":"91510106MA68YTDC0C","entContacts":"何仕明","entName":"成都煜晔建材有限公司","entAreaCode":"510106","createTime":"2021-03-29 17:21:49","modifyTime":"2021-03-29 17:21:49"}
         * status : true
         */

        @JsonProperty("user")
        private UserDTO user;
        @JsonProperty("status")
        private Boolean status;

        @NoArgsConstructor
        @Data
        public static class UserDTO {
            /**
             * id : 275314570317438976
             * isdel : 0
             * parentId : 2
             * userAccount : 91510106MA68YTDC0C
             * userName :
             * phone : 18030581373
             * userLabel : 企业上云
             * labelCode : 企业上云
             * email : 75095236@qq.com
             * userType : 9
             * cardNum :
             * sort : 0
             * status : 0
             * entNum : 91510106MA68YTDC0C
             * entContacts : 何仕明
             * entName : 成都煜晔建材有限公司
             * entAreaCode : 510106
             * createTime : 2021-03-29 17:21:49
             * modifyTime : 2021-03-29 17:21:49
             */

            @JsonProperty("id")
            private String id;
            @JsonProperty("isdel")
            private Integer isdel;
            @JsonProperty("parentId")
            private String parentId;
            @JsonProperty("userAccount")
            private String userAccount;
            @JsonProperty("userName")
            private String userName;
            @JsonProperty("phone")
            private String phone;
            @JsonProperty("userLabel")
            private String userLabel;
            @JsonProperty("labelCode")
            private String labelCode;
            @JsonProperty("email")
            private String email;
            @JsonProperty("userType")
            private String userType;
            @JsonProperty("cardNum")
            private String cardNum;
            @JsonProperty("sort")
            private Integer sort;
            @JsonProperty("status")
            private Integer status;
            @JsonProperty("entNum")
            private String entNum;
            @JsonProperty("entContacts")
            private String entContacts;
            @JsonProperty("entName")
            private String entName;
            @JsonProperty("entAreaCode")
            private String entAreaCode;
            @JsonProperty("createTime")
            private String createTime;
            @JsonProperty("modifyTime")
            private String modifyTime;
        }
    }
}
/*
                                      /[-])//  ___        
                                 __ --\ `_/~--|  / \      
                               /_-/~~--~~ /~~~\\_\ /\     
                               |  |___|===|_-- | \ \ \    
____________ _/~~~~~~~~|~~\,   ---|---\___/----|  \/\-\   
____________ ~\________|__/   / // \__ |  ||  / | |   | | 
                      ,~-|~~~~~\--, | \|--|/~|||  |   | | 
                      [3-|____---~~ _--'==;/ _,   |   |_| 
                                  /   /\__|_/  \  \__/--/ 
                                 /---/_\  -___/ |  /,--|  
                                 /  /\/~--|   | |  \///   
                                /  / |-__ \    |/         
                               |--/ /      |-- | \        
                              \^~~\\/\      \   \/- _     
                               \    |  \     |~~\~~| \    
                                \    \  \     \   \  | \  
                                  \    \ |     \   \    \ 
                                   |~~|\/\|     \   \   | 
                                  |   |/         \_--_- |\
                                  |  /            /   |/\/
                                   ~~             /  /    
                                                 |__/   W<

*/