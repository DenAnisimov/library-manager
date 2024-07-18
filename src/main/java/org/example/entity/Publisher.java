package org.example.entity;

import java.util.Objects;

public class Publisher {
    private int id;
    private String name;
    private String address;

    public Publisher() {}

    public Publisher(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publisher publisher)) return false;
        return id == publisher.id && Objects.equals(name, publisher.name) &&
                Objects.equals(address, publisher.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private String address;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Publisher build() {
            return new Publisher(this);
        }
    }
}
