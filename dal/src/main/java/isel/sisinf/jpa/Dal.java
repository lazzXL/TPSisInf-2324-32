/*
MIT License

Copyright (c) 2024, Nuno Datia, ISEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package isel.sisinf.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class Dal
{
    //For Demonstration purpose only
    public static String version(){ return "1.0";}
    @Entity
    public class Customer
    {
        @Id
        private int id;
        private String name;
        private String address;
        private String phone;
        private String email;

        public Customer(String name, String address, String phone, String email)
        {
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.email = email;
        }

        public String getName()
        {
            return name;
        }

        public String getAddress()
        {
            return address;
        }

        public String getPhone()
        {
            return phone;
        }

        public String getEmail()
        {
            return email;
        }
    }
}