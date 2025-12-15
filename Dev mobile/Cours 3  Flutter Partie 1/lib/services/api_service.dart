import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:getready/models/contact_model.dart';
import 'package:getready/models/message_model.dart';

class ApiService {
  static const String _baseUrl = 'http://192.168.12.7:3000';

  static Future<List<Contact>> getContacts() async {
    final response = await http.get(Uri.parse('$_baseUrl/contacts'));

    if (response.statusCode == 200) {
      List<dynamic> body = json.decode(response.body);
      return body.map((dynamic item) => Contact.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load contacts');
    }
  }

  static Future<List<Message>> getMessagesForContact(int contactId) async {
    final response = await http.get(Uri.parse('$_baseUrl/messages?contactID=$contactId'));

    if (response.statusCode == 200) {
      List<dynamic> body = json.decode(response.body);
      return body.map((dynamic item) => Message.fromJson(item)).toList();
    } else {
      throw Exception('Failed to load messages');
    }
  }

  static Future<Message> sendMessage(Message message) async {
    final response = await http.post(
      Uri.parse('$_baseUrl/messages'),
      headers: {'Content-Type': 'application/json'},
      body: json.encode(message.toJson()),
    );

    if (response.statusCode == 201) {
      return Message.fromJson(json.decode(response.body));
    } else {
      throw Exception('Failed to send message');
    }
  }
}