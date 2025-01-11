import {
  Injectable,
  computed,
  inject,
  signal,
  // toObservable,
} from '@angular/core';
import { toObservable } from '@angular/core/rxjs-interop';
import { Observable, Subject, defer, merge, of } from 'rxjs';
import { collection, query, orderBy, limit, addDoc } from 'firebase/firestore';
import { collectionData } from 'rxfire/firestore';
import {
  catchError,
  exhaustMap,
  filter,
  ignoreElements,
  map,
  retry,
} from 'rxjs/operators';
import { connect } from 'ngxtension/connect';

import { Message } from '../interfaces/message';
import { FIRESTORE } from '../../app.config';
import { AuthService } from './auth.service';

interface MessageState {
  messages: Message[];
  error: string | null;
}

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private firestore = inject(FIRESTORE);
  private authService = inject(AuthService);

  private authUser$ = toObservable(this.authService.user);

  // sources
  messages$ = this.getMessages().pipe(
    // restart stream when user reauthenticates
    retry({
      delay: () => this.authUser$.pipe(filter((user) => !!user)),
    })
  );

  // state
  private state = signal<MessageState>({
    messages: [],
    error: null,
  });

  // selectors
  messages = computed(() => this.state().messages);
  error = computed(() => this.state().error);

  add$ = new Subject<Message['content']>();

  constructor() {
    // reducers
    const nextState$ = merge(
      this.messages$.pipe(map((messages) => ({ messages }))),
      this.add$.pipe(
        exhaustMap((message) => this.addMessage(message)),
        ignoreElements(),
        catchError((error) => of({ error }))
      )
    );

    connect(this.state).with(nextState$);
  }

  private getMessages() {
    const messagesCollection = query(
      collection(this.firestore, 'messages'),
      orderBy('created', 'desc'),
      limit(50)
    );

    return collectionData(messagesCollection, { idField: 'id' }).pipe(
      map((messages) => [...messages].reverse())
    ) as Observable<Message[]>;
  }

  private addMessage(message: string) {
    const newMessage = {
      author: this.authService.user()?.email,
      content: message,
      created: Date.now().toString(),
    };

    const messagesCollection = collection(this.firestore, 'messages');
    return defer(() => addDoc(messagesCollection, newMessage));
  }
}
