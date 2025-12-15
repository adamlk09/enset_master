import 'package:flutter/material.dart';
import 'package:getready/models/contact_model.dart';
import 'package:getready/screens/contacts/messages_screen.dart';
import 'package:getready/services/api_service.dart';

class ContactsScreen extends StatefulWidget {
  const ContactsScreen({super.key});

  @override
  State<ContactsScreen> createState() => _ContactsScreenState();
}

class _ContactsScreenState extends State<ContactsScreen> {
  late Future<List<Contact>> _contactsFuture;

  @override
  void initState() {
    super.initState();
    _contactsFuture = ApiService.getContacts();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Contacts')),
      body: FutureBuilder<List<Contact>>(
        future: _contactsFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error} \n\n(Have you started the json-server and set the correct IP address?)'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No contacts found.'));
          }

          final contacts = snapshot.data!;
          return ListView.builder(
            itemCount: contacts.length,
            itemBuilder: (context, index) {
              final contact = contacts[index];
              return ListTile(
                leading: CircleAvatar(
                  backgroundImage: NetworkImage(contact.profile),
                ),
                title: Text(contact.name),
                subtitle: Text(contact.type),
                trailing: const Icon(Icons.message),
                onTap: () {
                  Navigator.of(context).push(MaterialPageRoute(
                    builder: (context) => MessagesScreen(contact: contact),
                  ));
                },
              );
            },
          );
        },
      ),
    );
  }
}