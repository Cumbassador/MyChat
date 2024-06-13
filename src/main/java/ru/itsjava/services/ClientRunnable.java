package ru.itsjava.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
@RequiredArgsConstructor
public class ClientRunnable implements Runnable,Observer{
    private final Socket socket;
    @SneakyThrows
    @Override
    public void run(){
        System.out.println("Client connected");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String messegeFromClient;
        while((messegeFromClient=bufferedReader.readLine())!=null){
            System.out.println( messegeFromClient);
        }
        System.out.println(bufferedReader.readLine());




    }

    @SneakyThrows
    @Override
    public void notifyMe(String messege) {
        PrintWriter clienWtitter = new PrintWriter(socket.getOutputStream());
        clienWtitter.println(messege);
        clienWtitter.flush();

    }
}