package com.app.xandone.yblogapp.model.bean;

import java.util.List;

/**
 * author: Admin
 * created on: 2020/10/21 16:53
 * description:
 */
public class ArtInfoBean {
    /**
     * typeBeans : [{"name":"编程","count":20},{"name":"杂文","count":21}]
     * artTypeBeans : [{"count":20,"typeName":"全部","type":-1},{"count":2,"typeName":"编程","type":0},{"count":7,"typeName":"Android","type":1},{"count":2,"typeName":"Java","type":2},{"count":3,"typeName":"前端","type":3},{"count":2,"typeName":"设计模式","type":4},{"count":3,"typeName":"算法","type":5},{"count":0,"typeName":"Python","type":6},{"count":1,"typeName":"Canvas","type":7},{"count":0,"typeName":"Game","type":8}]
     * yearArtData : [{"year":"2012","codeCount":0,"essayCount":1},{"year":"2013","codeCount":0,"essayCount":9},{"year":"2014","codeCount":0,"essayCount":2},{"year":"2015","codeCount":0,"essayCount":3},{"year":"2016","codeCount":3,"essayCount":1},{"year":"2017","codeCount":4,"essayCount":0},{"year":"2018","codeCount":4,"essayCount":0},{"year":"2019","codeCount":6,"essayCount":0},{"year":"2020","codeCount":3,"essayCount":5}]
     * commentCounts : 2
     */

    private int commentCounts;
    private List<TypeBeansBean> typeBeans;
    private List<ArtTypeBeansBean> artTypeBeans;
    private List<YearArtDataBean> yearArtData;

    public int getCommentCounts() {
        return commentCounts;
    }

    public void setCommentCounts(int commentCounts) {
        this.commentCounts = commentCounts;
    }

    public List<TypeBeansBean> getTypeBeans() {
        return typeBeans;
    }

    public void setTypeBeans(List<TypeBeansBean> typeBeans) {
        this.typeBeans = typeBeans;
    }

    public List<ArtTypeBeansBean> getArtTypeBeans() {
        return artTypeBeans;
    }

    public void setArtTypeBeans(List<ArtTypeBeansBean> artTypeBeans) {
        this.artTypeBeans = artTypeBeans;
    }

    public List<YearArtDataBean> getYearArtData() {
        return yearArtData;
    }

    public void setYearArtData(List<YearArtDataBean> yearArtData) {
        this.yearArtData = yearArtData;
    }

    public static class TypeBeansBean {
        /**
         * name : 编程
         * count : 20
         */

        private String name;
        private int count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class ArtTypeBeansBean {
        /**
         * count : 20
         * typeName : 全部
         * type : -1
         */

        private int count;
        private String typeName;
        private int type;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class YearArtDataBean {
        /**
         * year : 2012
         * codeCount : 0
         * essayCount : 1
         */

        private String year;
        private int codeCount;
        private int essayCount;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public int getCodeCount() {
            return codeCount;
        }

        public void setCodeCount(int codeCount) {
            this.codeCount = codeCount;
        }

        public int getEssayCount() {
            return essayCount;
        }

        public void setEssayCount(int essayCount) {
            this.essayCount = essayCount;
        }
    }
}
