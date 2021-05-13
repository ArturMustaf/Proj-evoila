import {DatafieldLoader} from './DatafieldLoader';
import {PopUpQuestion} from '../../models/database-form-container/PopUpQuestion';
import {TextBoxQuestion} from '../../models/questions/TextBoxQuestion';
import {DatabaseListContainer} from '../../models/database-list-container/DatabaseListContainer';
import {GenericForm} from '../../models/questions/GenericForm';

describe('DatafieldLoader Test', () => {
  const datafieldloader = new DatafieldLoader();
  const example: GenericForm<string>[] = [];
  beforeEach(() => {
    const datalist = new DatabaseListContainer<string>({key: 'phoneNumbers'});
    datalist.template = () => {
      return [new TextBoxQuestion({key: 'phoneNumber', label: 'Telefon-Nummer', value: '111'})];
    };
    datalist.list = [
      [new TextBoxQuestion({key: 'phoneNumber', label: 'Telefon-Nummer', value: '111'})],
      [new TextBoxQuestion({key: 'phoneNumber', label: 'Telefon-Nummer', value: '122'})]];
    example.push(new PopUpQuestion({
      key: 'Test', children: [
        new TextBoxQuestion({key: 'Test', label: 'Test', value: '1'}),
        new PopUpQuestion({
          key: 'A', label: 'Hallo', children: [
            new TextBoxQuestion({key: 'HI', label: 'HI', value: 'P'})
          ]
        }),
        datalist
      ]
    }));

  });

  it('Test creation of complex object && Calling Nested Value', () => {
    const result = datafieldloader.toFormGroup(example);
    expect(result.get(['Test', 'A', 'HI']).value === 'P').toBeTrue();
  });
  it('Test List Creation Fields', () => {
    const result = datafieldloader.toFormGroup(example);
    console.dir(result.get(['Test', 'phoneNumbers', '0', 'phoneNumber']));
    expect(result.get(['Test', 'phoneNumbers', '0', 'phoneNumber']).value === '111').toBeTrue();
    expect(result.get(['Test', 'phoneNumbers', '1', 'phoneNumber']).value === '122').toBeTrue();
  });

  it('Test Insertion of JSON', () => {
    const obj = {
      Test: {
        Test: 2,
        A: {HI: 'PETER'},
        phoneNumbers: [{phoneNumber: '3'}, {phoneNumber: '3'}, {phoneNumber: '3'}, {phoneNumber: '3'}]
      }
    };
    datafieldloader.fromJSON(obj, example);
    const result = datafieldloader.toFormGroup(example);
    expect(result.get(['Test', 'phoneNumbers', '0', 'phoneNumber']).value === '3').toBeTrue();
    expect(result.get(['Test', 'phoneNumbers', '3', 'phoneNumber']).value === '3').toBeTrue();
  });

  it('Test Creation of linkage', () => {

  });

});
