import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Doacao } from '../model-view/Doacao';

@Injectable({
    providedIn: 'root'
})
export class UtilsService {
    GerarHash(lenght = 7) {
        var result = '';
        var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        var charactersLength = characters.length;
        for (var i = 0; i < 7; i++) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
        }
        return result;
    }
}