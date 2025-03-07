import { Component, effect, Input, signal, ViewChild } from '@angular/core';
import { Message } from '../../shared/interfaces/message';
import { AuthUser } from '../../shared/data-access/auth.service';
import { NgStyle } from '@angular/common';
import { CdkScrollable, ScrollingModule } from '@angular/cdk/scrolling';

@Component({
  standalone: true,
  selector: 'app-message-list',
  template: `
    <ul cdkScrollable class="gradient-bg">
      @for (message of messagesSignal(); track message.created){
      <li
        [ngStyle]="{
          'flex-direction':
            message.author === activeUser?.email ? 'row-reverse' : 'row'
        }"
      >
        <div class="avatar animate-in-primary">
          <img
            src="https://api.dicebear.com/7.x/bottts/svg?seed={{
              message.author.split('@')[0]
            }}"
          />
        </div>
        <div class="message animate-in-secondary">
          <small>{{ message.author }}</small>
          <p>
            {{ message.content }}
          </p>
        </div>
      </li>
      }
    </ul>
  `,
  imports: [NgStyle, ScrollingModule],
  styles: [
    `
      ul {
        height: 100%;
        overflow: scroll;
        list-style-type: none;
        padding: 1rem;
        padding-bottom: 5rem;
        margin: 0;
      }

      li {
        display: flex;
        margin-bottom: 2rem;
      }

      .avatar {
        width: 75px;
        margin: 0 1rem;
        height: auto;
        filter: drop-shadow(2px 3px 5px var(--accent-darker-color));
      }

      .message {
        width: 100%;
        background: var(--white);
        padding: 2rem;
        border-radius: 5px;
        filter: drop-shadow(2px 4px 3px var(--primary-darker-color));
      }
    `,
  ],
})
export class MessageListComponent {
  messagesSignal = signal<Message[]>([]);

  @Input({ required: true }) set messages(value: Message[]) {
    this.messagesSignal.set(value);
  }
  @Input({ required: true }) activeUser!: AuthUser;
  @ViewChild(CdkScrollable) scrollContainer!: CdkScrollable;

  constructor() {
    effect(() => {
      if (this.messagesSignal().length && this.scrollContainer) {
        this.scrollContainer.scrollTo({
          bottom: 0,
          behavior: 'smooth',
        });
      }
    });
  }
}
