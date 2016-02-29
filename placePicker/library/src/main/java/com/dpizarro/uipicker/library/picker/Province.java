package com.dpizarro.uipicker.library.picker;

import java.util.List;

/**
 * Created by Administrator on 2016/1/13.
 */
public class Province {

    private String name;
    private List<City> city;

    public List<City> getCities() {
        return city;
    }

    public void setCities(List<City> cities) {
        this.city = cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class City{
        private String name;
        private List<String> area;

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> counties) {
            this.area = counties;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
