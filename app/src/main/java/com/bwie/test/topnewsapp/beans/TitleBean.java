package com.bwie.test.topnewsapp.beans;

import java.util.List;

/**
 * Created by tianjieyu on 2017/4/11.
 */

public class TitleBean {

    /**
     * reason : success
     * result : {"stat":"1","date":[{"id":6,"uri":"tt","title":"头条"},{"id":8,"uri":"shehui","title":"社会"},{"id":12,"uri":"gn","title":"国内"},{"id":13,"uri":"gj","title":"国际"},{"id":22,"uri":"yl","title":"娱乐"},{"id":23,"uri":"ty","title":"体育"},{"id":25,"uri":"js","title":"军事"},{"id":26,"uri":"kj","title":"科技"},{"id":36,"uri":"cj","title":"财经"},{"id":38,"uri":"ss","title":"时尚"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * stat : 1
         * date : [{"id":6,"uri":"tt","title":"头条"},{"id":8,"uri":"shehui","title":"社会"},{"id":12,"uri":"gn","title":"国内"},{"id":13,"uri":"gj","title":"国际"},{"id":22,"uri":"yl","title":"娱乐"},{"id":23,"uri":"ty","title":"体育"},{"id":25,"uri":"js","title":"军事"},{"id":26,"uri":"kj","title":"科技"},{"id":36,"uri":"cj","title":"财经"},{"id":38,"uri":"ss","title":"时尚"}]
         */

        private String stat;
        private List<DateBean> date;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<DateBean> getDate() {
            return date;
        }

        public void setDate(List<DateBean> date) {
            this.date = date;
        }

        public static class DateBean {
            /**
             * id : 6
             * uri : tt
             * title : 头条
             */

            private int id;
            private String uri;
            private String title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUri() {
                return uri;
            }

            public void setUri(String uri) {
                this.uri = uri;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
