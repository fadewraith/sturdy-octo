export interface ListGroupType {
  items: string[];
  heading: string;
  onSelectItem: (item: string) => void;
}
